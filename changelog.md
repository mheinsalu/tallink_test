ver-1.2.0
    JSON requests to API now send IDs (conferenceId, conferenceRoomId, participantId) instead of entire models. This required a large rewrite of code and tests.
        Changed exampleJSONs.txt as well to reflect new approach.
    Added tests for FrontEndController, FrontEndServiceImpl and RepoHelper.
    Changed frontend to send new style of JSON requests.
    