package net.csthings.antreminder.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.csthings.antreminder.entity.dto.AppInfoDto;

@Repository
@Transactional
public interface AppInfoDao extends JpaRepository<AppInfoDto, String> {
}
