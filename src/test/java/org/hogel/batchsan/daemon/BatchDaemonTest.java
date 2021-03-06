package org.hogel.batchsan.daemon;

public class BatchDaemonTest {
//    @Inject
//    JobRecipeLogDao jobRecipeLogTable;
//
//    @Inject
//    BoneCP connections;
//
//    private TestBatchConfig config;
//
//    private BatchJobManager batchJobManager;
//
//    @Before
//    public void before() throws Exception {
//        config = new TestBatchConfig();
//        config.load("{}");
//
//        batchJobManager = new BatchJobManager(config);
//
//        Injector injector = batchJobManager.getInjector();
//        injector.injectMembers(this);
//    }
//
//    @Test
//    public void runWithoutJobRecipeId() throws Exception {
//        BatchDaemon daemon = new BatchDaemon(batchJobManager);
//        try (Connection connection = connections.getConnection()) {
//            jobRecipeLogTable.truncate(connection);
//
//            daemon.executeJob("job: nop");
//
//            List<JobRecipeLogRecord> jobRecipeLogs = jobRecipeLogTable.getAll(connection);
//            assertThat(jobRecipeLogs.get(0).getJob(), is("nop"));
//            assertThat(jobRecipeLogs.get(0).getStatus(), is(JobRecipeLogDao.SUCCESS));
//            assertThat(jobRecipeLogs.get(0).getJob_recipe_id(), is(nullValue()));
//        }
//    }
//
//    @Test
//    public void runWithJobRecipeId() throws Exception {
//        BatchDaemon daemon = new BatchDaemon(batchJobManager);
//        try (Connection connection = connections.getConnection()) {
//            jobRecipeLogTable.truncate(connection);
//
//            daemon.executeJob("{job: nop, options: {job_recipe_id: 10}}");
//
//            List<JobRecipeLogRecord> jobRecipeLogs = jobRecipeLogTable.getAll(connection);
//            assertThat(jobRecipeLogs.get(0).getJob(), is("nop"));
//            assertThat(jobRecipeLogs.get(0).getStatus(), is(JobRecipeLogDao.SUCCESS));
//            assertThat(jobRecipeLogs.get(0).getJob_recipe_id(), is(10L));
//        }
//    }
}
