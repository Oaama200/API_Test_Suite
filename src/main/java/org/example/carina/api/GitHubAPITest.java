package org.example.carina.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.ResponseTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import org.testng.annotations.Test;
import com.zebrunner.carina.core.AbstractTest;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class GitHubAPITest extends AbstractTest {
    private static final String BASE_URL = "https://api.github.com";
    private static final String TOKEN = System.getProperty("github.token", "Mytoken");
    private static final String USERNAME = "username";

    private String generateUniqueRepoName() {
        return "api-test-repo-" + System.currentTimeMillis();
    }

    @Test(priority = 1)
    public void testCreateRepository() {
        String repoName = generateUniqueRepoName();
        CreateRepoMethod createRepo = new CreateRepoMethod();
        createRepo.addProperty("name", repoName);
        createRepo.addProperty("description", "Test repository");
        createRepo.addProperty("private", "false");
        createRepo.callAPI();
        createRepo.validateResponse(JSONCompareMode.LENIENT);

        // Cleanup
        try {
            DeleteRepoMethod deleteRepo = new DeleteRepoMethod(USERNAME, repoName);
            deleteRepo.callAPI();
        } catch (Exception e) {
            System.out.print("Failed to delete repository: " + repoName);        }
    }

    @Test(priority = 2)
    public void testGetRepository() {
        // Setup
        String repoName = generateUniqueRepoName();
        CreateRepoMethod createRepo = new CreateRepoMethod();
        createRepo.addProperty("name", repoName);
        createRepo.addProperty("description", "Test repository");
        createRepo.addProperty("private", "false");
        createRepo.callAPI();

        // Test
        GetRepoMethod getRepo = new GetRepoMethod(repoName);
        getRepo.callAPI();
        getRepo.validateResponse(JSONCompareMode.LENIENT);

        // Cleanup
        try {
            DeleteRepoMethod deleteRepo = new DeleteRepoMethod(USERNAME, repoName);
            deleteRepo.callAPI();
        } catch (Exception e) {
            System.out.print("Failed to delete repository: " + repoName);        }
    }

    @Test(priority = 3)
    public void testUpdateRepository() {
        // Setup
        String repoName = generateUniqueRepoName();
        CreateRepoMethod createRepo = new CreateRepoMethod();
        createRepo.addProperty("name", repoName);
        createRepo.addProperty("description", "Test repository");
        createRepo.addProperty("private", "false");
        createRepo.callAPI();

        // Test
        UpdateRepoMethod updateRepo = new UpdateRepoMethod(repoName);
        updateRepo.addProperty("description", "Updated description");
        updateRepo.callAPI();
        updateRepo.validateResponse(JSONCompareMode.LENIENT);

        // Cleanup
        try {
            DeleteRepoMethod deleteRepo = new DeleteRepoMethod(USERNAME, repoName);
            deleteRepo.callAPI();
        } catch (Exception e) {
            System.out.print("Failed to delete repository: " + repoName);        }
    }

    @Test(priority = 4)
    public void testDeleteRepository() {
        // Setup
        String repoName = generateUniqueRepoName();
        CreateRepoMethod createRepo = new CreateRepoMethod();
        createRepo.addProperty("name", repoName);
        createRepo.addProperty("description", "Test repository");
        createRepo.addProperty("private", "false");
        createRepo.callAPI();

        // Test
        DeleteRepoMethod deleteRepo = new DeleteRepoMethod(USERNAME, repoName);
        deleteRepo.callAPI();
    }

    @Test(priority = 5)
    public void testCreateIssue() {
        // Setup
        String repoName = generateUniqueRepoName();
        CreateRepoMethod createRepo = new CreateRepoMethod();
        createRepo.addProperty("name", repoName);
        createRepo.addProperty("description", "Test repository");
        createRepo.addProperty("private", "false");
        createRepo.callAPI();

        // Test
        CreateIssueMethod createIssue = new CreateIssueMethod(repoName);
        createIssue.addProperty("title", "Test Issue");
        createIssue.addProperty("body", "This is a test issue description");
        createIssue.callAPI();
        createIssue.validateResponse(JSONCompareMode.LENIENT);

        // Cleanup
        try {
            DeleteRepoMethod deleteRepo = new DeleteRepoMethod(USERNAME, repoName);
            deleteRepo.callAPI();
        } catch (Exception e) {
            System.out.print("Failed to delete repository: " + repoName);        }
    }

    @Test(priority = 6)
    public void testGetIssues() {
        // Setup
        String repoName = generateUniqueRepoName();
        CreateRepoMethod createRepo = new CreateRepoMethod();
        createRepo.addProperty("name", repoName);
        createRepo.addProperty("description", "Test repository");
        createRepo.addProperty("private", "false");
        createRepo.callAPI();

        CreateIssueMethod createIssue = new CreateIssueMethod(repoName);
        createIssue.addProperty("title", "Test Issue");
        createIssue.addProperty("body", "This is a test issue description");
        createIssue.callAPI();

        // Test
        GetIssuesMethod getIssues = new GetIssuesMethod(repoName);
        getIssues.callAPI();
        getIssues.validateResponse(JSONCompareMode.LENIENT);

        // Cleanup
        try {
            DeleteRepoMethod deleteRepo = new DeleteRepoMethod(USERNAME, repoName);
            deleteRepo.callAPI();
        } catch (Exception e) {
            System.out.print("Failed to delete repository: " + repoName);        }
    }

    @Test(priority = 7)
    public void testCreatePullRequest() {
        // Setup
        String repoName = generateUniqueRepoName();
        CreateRepoMethod createRepo = new CreateRepoMethod();
        createRepo.addProperty("name", repoName);
        createRepo.addProperty("description", "Test repository");
        createRepo.addProperty("private", "false");
        createRepo.addProperty("auto_init", "true");
        createRepo.callAPI();

        // Test
        CreatePullRequestMethod createPR = new CreatePullRequestMethod(repoName);
        createPR.addProperty("title", "Test Pull Request");
        createPR.addProperty("head", "main");
        createPR.addProperty("base", "main");
        createPR.addProperty("body", "This is a test pull request");
        createPR.callAPI();
        createPR.validateResponse(JSONCompareMode.LENIENT);

        // Cleanup
        try {
            DeleteRepoMethod deleteRepo = new DeleteRepoMethod(USERNAME, repoName);
            deleteRepo.callAPI();
        } catch (Exception e) {
            System.out.print("Failed to delete repository: " + repoName);        }
    }

    @Test(priority = 8)
    public void testGetRepositoryLanguages() {
        // Setup
        String repoName = generateUniqueRepoName();
        CreateRepoMethod createRepo = new CreateRepoMethod();
        createRepo.addProperty("name", repoName);
        createRepo.addProperty("description", "Test repository");
        createRepo.addProperty("private", "false");
        createRepo.addProperty("auto_init", "true");
        createRepo.callAPI();

        // Test
        GetLanguagesMethod getLanguages = new GetLanguagesMethod(repoName);
        getLanguages.callAPI();
        getLanguages.validateResponse(JSONCompareMode.LENIENT);

        // Cleanup
        try {
            DeleteRepoMethod deleteRepo = new DeleteRepoMethod(USERNAME, repoName);
            deleteRepo.callAPI();
        } catch (Exception e) {
            System.out.print("Failed to delete repository: " + repoName);
        }
    }

    @Test(priority = 9)
    public void testGetRepositoryContributors() {
        // Setup
        String repoName = generateUniqueRepoName();
        CreateRepoMethod createRepo = new CreateRepoMethod();
        createRepo.addProperty("name", repoName);
        createRepo.addProperty("description", "Test repository");
        createRepo.addProperty("private", "false");
        createRepo.addProperty("auto_init", "true");
        createRepo.callAPI();

        // Test
        GetContributorsMethod getContributors = new GetContributorsMethod(repoName);
        getContributors.callAPI();
        getContributors.validateResponse(JSONCompareMode.LENIENT);

        // Cleanup
        try {
            DeleteRepoMethod deleteRepo = new DeleteRepoMethod(USERNAME, repoName);
            deleteRepo.callAPI();
        } catch (Exception e) {
            System.out.print("Failed to delete repository: " + repoName);
        }
    }

    @Test(priority = 10)
    public void testGetRepositoryBranches() {
        // Setup
        String repoName = generateUniqueRepoName();
        CreateRepoMethod createRepo = new CreateRepoMethod();
        createRepo.addProperty("name", repoName);
        createRepo.addProperty("description", "Test repository");
        createRepo.addProperty("private", "false");
        createRepo.addProperty("auto_init", "true");
        createRepo.callAPI();

        // Test
        GetBranchesMethod getBranches = new GetBranchesMethod(repoName);
        getBranches.callAPI();
        getBranches.validateResponse(JSONCompareMode.LENIENT);

        // Cleanup
        try {
            DeleteRepoMethod deleteRepo = new DeleteRepoMethod(USERNAME, repoName);
            deleteRepo.callAPI();
        } catch (Exception e) {
            System.out.print("Failed to delete repository: " + repoName);        }
    }

    @Endpoint(url = "${base_url}/user/repos", methodType = HttpMethodType.POST)
    @RequestTemplatePath(path = "api/repos/create/rq.json")
    @ResponseTemplatePath(path = "api/repos/create/rs.json")
    @SuccessfulHttpStatus(status = HttpResponseStatusType.CREATED_201)
    public class CreateRepoMethod extends AbstractApiMethodV2 {
        public CreateRepoMethod() {
            super("api/repos/create/rq.json", "api/repos/create/rs.json", "api/repos/create/create.properties");
            replaceUrlPlaceholder("base_url", BASE_URL);
            setHeaders("Authorization=Bearer " + TOKEN);
        }
    }

    @Endpoint(url = "${base_url}/repos/${username}/${repo}", methodType = HttpMethodType.GET)
    @ResponseTemplatePath(path = "api/repos/get/rs.json")
    @SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
    public class GetRepoMethod extends AbstractApiMethodV2 {
        public GetRepoMethod(String repo) {
            super(null, "api/repos/get/rs.json", "api/repos/get/get.properties");
            replaceUrlPlaceholder("base_url", BASE_URL);
            replaceUrlPlaceholder("username", "Oaama200");
            //replaceUrlPlaceholder("username", System.getProperty("github.username"));
            replaceUrlPlaceholder("repo", repo);
            setHeaders("Authorization=Bearer " + TOKEN);
        }
    }

    @Endpoint(url = "${base_url}/repos/${username}/${repo}", methodType = HttpMethodType.PATCH)
    @RequestTemplatePath(path = "api/repos/update/rq.json")
    @ResponseTemplatePath(path = "api/repos/update/rs.json")
    @SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
    public class UpdateRepoMethod extends AbstractApiMethodV2 {
        public UpdateRepoMethod(String repo) {
            super("api/repos/update/rq.json", "api/repos/update/rs.json", "api/repos/update/update.properties");
            replaceUrlPlaceholder("base_url", BASE_URL);
            //replaceUrlPlaceholder("username", System.getProperty("github.username"));
            replaceUrlPlaceholder("username", "Oaama200");
            replaceUrlPlaceholder("repo", repo);
            setHeaders("Authorization=Bearer " + TOKEN);
        }
    }

    @Endpoint(url = "${base_url}/repos/${username}/${repo}", methodType = HttpMethodType.DELETE)
    @SuccessfulHttpStatus(status = HttpResponseStatusType.NO_CONTENT_204)
    public class DeleteRepoMethod extends AbstractApiMethodV2 {
        public DeleteRepoMethod(String username, String repoName) {
            super();
            replaceUrlPlaceholder("base_url", BASE_URL);
            replaceUrlPlaceholder("username", username);
            replaceUrlPlaceholder("repo", repoName);
            setHeaders("Authorization=Bearer " + TOKEN);
        }

    }
    @Endpoint(url = "${base_url}/repos/${username}/${repo}/issues", methodType = HttpMethodType.POST)
    @RequestTemplatePath(path = "api/issues/create/rq.json")
    @ResponseTemplatePath(path = "api/issues/create/rs.json")
    @SuccessfulHttpStatus(status = HttpResponseStatusType.CREATED_201)
    public class CreateIssueMethod extends AbstractApiMethodV2 {
        public CreateIssueMethod(String repo) {
            super("api/issues/create/rq.json", "api/issues/create/rs.json", "api/issues/create/create.properties");
            replaceUrlPlaceholder("base_url", BASE_URL);
            replaceUrlPlaceholder("username", USERNAME);
            replaceUrlPlaceholder("repo", repo);
            setHeaders("Authorization=Bearer " + TOKEN);
        }
    }

    @Endpoint(url = "${base_url}/repos/${username}/${repo}/issues", methodType = HttpMethodType.GET)
    @ResponseTemplatePath(path = "api/issues/get/rs.json")
    @SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
    public class GetIssuesMethod extends AbstractApiMethodV2 {
        public GetIssuesMethod(String repo) {
            super(null, "api/issues/get/rs.json", "api/issues/get/get.properties");
            replaceUrlPlaceholder("base_url", BASE_URL);
            replaceUrlPlaceholder("username", USERNAME);
            replaceUrlPlaceholder("repo", repo);
            setHeaders("Authorization=Bearer " + TOKEN);
        }
    }

    @Endpoint(url = "${base_url}/repos/${username}/${repo}/pulls", methodType = HttpMethodType.POST)
    @RequestTemplatePath(path = "api/pulls/create/rq.json")
    @ResponseTemplatePath(path = "api/pulls/create/rs.json")
    @SuccessfulHttpStatus(status = HttpResponseStatusType.CREATED_201)
    public class CreatePullRequestMethod extends AbstractApiMethodV2 {
        public CreatePullRequestMethod(String repo) {
            super("api/pulls/create/rq.json", "api/pulls/create/rs.json", "api/pulls/Create/create.properties");
            replaceUrlPlaceholder("base_url", BASE_URL);
            replaceUrlPlaceholder("username", USERNAME);
            replaceUrlPlaceholder("repo", repo);
            setHeaders("Authorization=Bearer " + TOKEN);
            setHeaders("Accept=application/vnd.github.v3+json");
        }
    }

    @Endpoint(url = "${base_url}/repos/${username}/${repo}/languages", methodType = HttpMethodType.GET)
    @ResponseTemplatePath(path = "api/languages/rs.json")
    @SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
    public class GetLanguagesMethod extends AbstractApiMethodV2 {
        public GetLanguagesMethod(String repo) {
            super(null, "api/languages/rs.json", "api/languages/languages.properties");
            replaceUrlPlaceholder("base_url", BASE_URL);
            replaceUrlPlaceholder("username", USERNAME);
            replaceUrlPlaceholder("repo", repo);
            setHeaders("Authorization=Bearer " + TOKEN);
            setHeaders("Accept=application/vnd.github.v3+json");
        }
    }

    @Endpoint(url = "${base_url}/repos/${username}/${repo}/contributors", methodType = HttpMethodType.GET)
    @ResponseTemplatePath(path = "api/contributors/rs.json")
    @SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
    public class GetContributorsMethod extends AbstractApiMethodV2 {
        public GetContributorsMethod(String repo) {
            super(null, "api/contributors/rs.json", "api/contributors/contributors.properties");
            replaceUrlPlaceholder("base_url", BASE_URL);
            replaceUrlPlaceholder("username", USERNAME);
            replaceUrlPlaceholder("repo", repo);
            setHeaders("Authorization=Bearer " + TOKEN);
        }
    }
    @Endpoint(url = "${base_url}/repos/${username}/${repo}/branches", methodType = HttpMethodType.GET)
    @ResponseTemplatePath(path = "api/branches/rs.json")
    @SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
    public class GetBranchesMethod extends AbstractApiMethodV2 {
        public GetBranchesMethod(String repo) {
            super(null, "api/branches/rs.json", "api/branches/branches.properties");
            replaceUrlPlaceholder("base_url", BASE_URL);
            replaceUrlPlaceholder("username", USERNAME);
            replaceUrlPlaceholder("repo", repo);
            setHeaders("Authorization=Bearer " + TOKEN);
        }
    }
}
