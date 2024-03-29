package ru.job4j.carsales.repo;

import ru.job4j.carsales.dto.AnnouncementDto;
import ru.job4j.carsales.dto.ModelDto;
import ru.job4j.carsales.model.*;
import ru.job4j.carsales.repo.filter.AdFilter;

import java.util.List;
import java.util.Set;

public class Repo {
    private AccountRepo accountRepo;
    private AnnouncementRepo announcementRepo;
    private CarRepo carRepo;
    private EngineRepo engineRepo;
    private MarkRepo markRepo;
    private ModelRepo modelRepo;
    private TransmissionRepo transmissionRepo;

    public Repo() {
        Store store = new Store();
        this.accountRepo = AccountRepo.of(store);
        this.announcementRepo = AnnouncementRepo.of(store);
        this.carRepo = CarRepo.of(store);
        this.engineRepo = EngineRepo.of(store);
        this.markRepo = MarkRepo.of(store);
        this.modelRepo = ModelRepo.of(store);
        this.transmissionRepo = TransmissionRepo.of(store);
    }

    public boolean create(Account account) {
        return accountRepo.create(account);
    }

    public void update(Account account) {
        accountRepo.update(account);
    }

    public Account findAccountByEmail(String email) {
        return accountRepo.findAccountByEmail(email);
    }

    public Account findAccountById(Long id) {
        return accountRepo.findAccountById(id);
    }

    public List<AnnouncementDto> findAllAnnouncements(AdFilter filter) {
        return announcementRepo.findAllAnnouncements(filter);
    }

    public List<AnnouncementDto> findAnnouncementsOfAccount(Long accountId) {
        return announcementRepo.findAnnouncementsOfAccount(accountId);
    }

    public AnnouncementDto findAnnouncementById(Long id) {
        return announcementRepo.findAnnouncementById(id);
    }

    public ModelDto findModelDtoById(Long modelId) {
        return modelRepo.findModelDtoById(modelId);
    }

    public Engine findEngineById(Long engineId) {
        return engineRepo.findEngineById(engineId);
    }

    public Transmission findTransmissionById(Long transmissionId) {
        return transmissionRepo.findTransmissionById(transmissionId);
    }


    public List<Transmission> findAllTm() {
        return transmissionRepo.findAllTm();
    }

    public boolean create(Transmission tm) {
        return transmissionRepo.create(tm);
    }

    public boolean create(Announcement announcement) {
        return announcementRepo.create(announcement);
    }

    public Set<Engine> findAllEnginesOfModel(long modelId) {
        return engineRepo.findAllEnginesOfModel(modelId);
    }

    public List<Engine> findAllEngines() {
        return engineRepo.findAllEngines();
    }

    public boolean create(Engine engine) {
        return engineRepo.create(engine);
    }

    public boolean applyEngineToModel(Long modelId, Long engineId) {
        return engineRepo.applyEngineToModel(modelId, engineId);
    }

    public boolean deleteEngineFromModel(Long modelId, Long engineId) {
        return engineRepo.deleteEngineFromModel(modelId, engineId);
    }

    public List<ModelDto> findModelsByMarkId(long markId) {
        return modelRepo.findModelsByMarkId(markId);
    }

    public boolean create(Model model) {
        return modelRepo.create(model);
    }

    public List<Mark> findAllMarks() {
        return markRepo.findAllMarks();
    }

    public boolean create(Mark mark) {
        return markRepo.create(mark);
    }

    public Mark findMarkById(Long markId) {
        return markRepo.findMarkById(markId);
    }

    public boolean tryCloseAd(Long accountId, Long announcementId) {
        return announcementRepo.tryCloseAd(accountId, announcementId);
    }
}
