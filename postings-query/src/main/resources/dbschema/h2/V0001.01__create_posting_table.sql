CREATE TABLE Posting (
	postingId BIGINT PRIMARY KEY,

	userId BIGINT,
	createdAt TIMESTAMP,
	updatedAt TIMESTAMP,
	lastClientEventId VARCHAR(32),
	lastPostingEventId BIGINT,

	postingText CLOB
);
