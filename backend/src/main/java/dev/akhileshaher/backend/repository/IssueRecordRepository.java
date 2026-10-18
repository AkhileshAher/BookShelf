package dev.akhileshaher.backend.repository;

import dev.akhileshaher.backend.entity.IssueRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRecordRepository extends JpaRepository<IssueRecord,Long> {
}
