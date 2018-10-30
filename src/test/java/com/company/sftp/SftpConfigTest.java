package com.company.sftp;

import static junit.framework.TestCase.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)

public class SftpConfigTest {
	private static EmbeddedSftpServer server;
	private static Path sftpFolder;

	@Autowired
	protected SftpProperties sftpProperties;

	@BeforeClass
    public static void startServer() throws Exception {
		server = new EmbeddedSftpServer();
		server.setPort(10022);
		sftpFolder = Files.createTempDirectory("SFTP_DOWNLOAD_TEST");
		server.afterPropertiesSet();
		server.setHomeFolder(sftpFolder);
		// Starting SFTP
		if (!server.isRunning()) {
			server.start();
		}
	}

	@Before
	@After
	public void clean() throws Exception {
		Files.walk(Paths.get(sftpProperties.getDestination())).filter(Files::isRegularFile).map(Path::toFile)
				.forEach(File::delete);
	}

	@Test
    @Ignore
	public void testDownload() throws IOException, InterruptedException, ExecutionException, TimeoutException {
	    // Prepare phase
		Path tempFile = Files.createTempFile(sftpFolder, "SDP_NPAT_TRAFFICOAGGREGATO_DELTA_json", ".gz");
        System.out.println(String.format("testDownload, tempFile created at %s", tempFile));

		// Run async task to wait for expected files to be downloaded to a file
		// system from a remote SFTP server
		Future<Boolean> future = Executors.newSingleThreadExecutor().submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				Path expectedFile = Paths.get(sftpProperties.getDestination())
						.resolve(tempFile.getFileName());

                System.out.println(String.format("expectedFile, at %s", expectedFile));
                System.out.println(String.format("expectedFile, at %s", expectedFile.toAbsolutePath()));

				while (!Files.exists(expectedFile)) {
					Thread.sleep(200);
				}

				return true;
			}
		});

		// Validation phase
		assertTrue(future.get(10, TimeUnit.SECONDS));
	}
	
	@Test
	public void testMoveRemoteFile() throws IOException, InterruptedException, ExecutionException, TimeoutException {
		// Prepare phase
		Path tempFile = Files.createTempFile(sftpFolder, "SDP_NPAT_TEST_DELTA_json", ".gz");

        Path expectedDownloadedFile = Paths.get(sftpProperties.getDestination())
                .resolve(tempFile.getFileName());

		// Run async task to wait for expected files to be downloaded to a file
		// system from a remote SFTP server
		Future<Boolean> future = Executors.newSingleThreadExecutor().submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				Path expectedFile = Paths.get(sftpFolder.toString())
						.resolve(sftpProperties.getCache())
						.resolve(tempFile.getFileName());

				while (!Files.exists(expectedFile)) {
					Thread.sleep(200);
				}

				return true;
			}
		});

		// Validation phase
		assertTrue(future.get(10, TimeUnit.SECONDS));
		assertTrue(Files.notExists(tempFile));
		assertTrue(Files.exists(expectedDownloadedFile));
	}

	@AfterClass
	public static void stopServer() {
		if (server.isRunning()) {
			server.stop();
		}
	}
}