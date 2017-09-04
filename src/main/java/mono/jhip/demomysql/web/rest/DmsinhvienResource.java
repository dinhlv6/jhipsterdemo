package mono.jhip.demomysql.web.rest;

import com.codahale.metrics.annotation.Timed;
import mono.jhip.demomysql.domain.Dmsinhvien;

import mono.jhip.demomysql.repository.DmsinhvienRepository;
import mono.jhip.demomysql.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Dmsinhvien.
 */
@RestController
@RequestMapping("/api")
public class DmsinhvienResource {

    private final Logger log = LoggerFactory.getLogger(DmsinhvienResource.class);

    private static final String ENTITY_NAME = "dmsinhvien";

    private final DmsinhvienRepository dmsinhvienRepository;
    public DmsinhvienResource(DmsinhvienRepository dmsinhvienRepository) {
        this.dmsinhvienRepository = dmsinhvienRepository;
    }

    /**
     * POST  /dmsinhviens : Create a new dmsinhvien.
     *
     * @param dmsinhvien the dmsinhvien to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dmsinhvien, or with status 400 (Bad Request) if the dmsinhvien has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dmsinhviens")
    @Timed
    public ResponseEntity<Dmsinhvien> createDmsinhvien(@Valid @RequestBody Dmsinhvien dmsinhvien) throws URISyntaxException {
        log.debug("REST request to save Dmsinhvien : {}", dmsinhvien);
        if (dmsinhvien.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dmsinhvien cannot already have an ID")).body(null);
        }
        Dmsinhvien result = dmsinhvienRepository.save(dmsinhvien);
        return ResponseEntity.created(new URI("/api/dmsinhviens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dmsinhviens : Updates an existing dmsinhvien.
     *
     * @param dmsinhvien the dmsinhvien to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dmsinhvien,
     * or with status 400 (Bad Request) if the dmsinhvien is not valid,
     * or with status 500 (Internal Server Error) if the dmsinhvien couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dmsinhviens")
    @Timed
    public ResponseEntity<Dmsinhvien> updateDmsinhvien(@Valid @RequestBody Dmsinhvien dmsinhvien) throws URISyntaxException {
        log.debug("REST request to update Dmsinhvien : {}", dmsinhvien);
        if (dmsinhvien.getId() == null) {
            return createDmsinhvien(dmsinhvien);
        }
        Dmsinhvien result = dmsinhvienRepository.save(dmsinhvien);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dmsinhvien.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dmsinhviens : get all the dmsinhviens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dmsinhviens in body
     */
    @GetMapping("/dmsinhviens")
    @Timed
    public List<Dmsinhvien> getAllDmsinhviens() {
        log.debug("REST request to get all Dmsinhviens");
        return dmsinhvienRepository.findAll();
        }

    /**
     * GET  /dmsinhviens/:id : get the "id" dmsinhvien.
     *
     * @param id the id of the dmsinhvien to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dmsinhvien, or with status 404 (Not Found)
     */
    @GetMapping("/dmsinhviens/{id}")
    @Timed
    public ResponseEntity<Dmsinhvien> getDmsinhvien(@PathVariable Long id) {
        log.debug("REST request to get Dmsinhvien : {}", id);
        Dmsinhvien dmsinhvien = dmsinhvienRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dmsinhvien));
    }

    /**
     * DELETE  /dmsinhviens/:id : delete the "id" dmsinhvien.
     *
     * @param id the id of the dmsinhvien to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dmsinhviens/{id}")
    @Timed
    public ResponseEntity<Void> deleteDmsinhvien(@PathVariable Long id) {
        log.debug("REST request to delete Dmsinhvien : {}", id);
        dmsinhvienRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
