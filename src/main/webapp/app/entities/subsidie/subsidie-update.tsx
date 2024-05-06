import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IZaak } from 'app/shared/model/zaak.model';
import { getEntities as getZaaks } from 'app/entities/zaak/zaak.reducer';
import { ISector } from 'app/shared/model/sector.model';
import { getEntities as getSectors } from 'app/entities/sector/sector.reducer';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { getEntities as getMedewerkers } from 'app/entities/medewerker/medewerker.reducer';
import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';
import { getEntities as getRechtspersoons } from 'app/entities/rechtspersoon/rechtspersoon.reducer';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { getEntities as getKostenplaats } from 'app/entities/kostenplaats/kostenplaats.reducer';
import { IDocument } from 'app/shared/model/document.model';
import { getEntities as getDocuments } from 'app/entities/document/document.reducer';
import { ISubsidie } from 'app/shared/model/subsidie.model';
import { getEntity, updateEntity, createEntity, reset } from './subsidie.reducer';

export const SubsidieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const zaaks = useAppSelector(state => state.zaak.entities);
  const sectors = useAppSelector(state => state.sector.entities);
  const medewerkers = useAppSelector(state => state.medewerker.entities);
  const rechtspersoons = useAppSelector(state => state.rechtspersoon.entities);
  const kostenplaats = useAppSelector(state => state.kostenplaats.entities);
  const documents = useAppSelector(state => state.document.entities);
  const subsidieEntity = useAppSelector(state => state.subsidie.entity);
  const loading = useAppSelector(state => state.subsidie.loading);
  const updating = useAppSelector(state => state.subsidie.updating);
  const updateSuccess = useAppSelector(state => state.subsidie.updateSuccess);

  const handleClose = () => {
    navigate('/subsidie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getZaaks({}));
    dispatch(getSectors({}));
    dispatch(getMedewerkers({}));
    dispatch(getRechtspersoons({}));
    dispatch(getKostenplaats({}));
    dispatch(getDocuments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.cofinanciering !== undefined && typeof values.cofinanciering !== 'number') {
      values.cofinanciering = Number(values.cofinanciering);
    }
    if (values.hoogtesubsidie !== undefined && typeof values.hoogtesubsidie !== 'number') {
      values.hoogtesubsidie = Number(values.hoogtesubsidie);
    }
    if (values.ontvangenbedrag !== undefined && typeof values.ontvangenbedrag !== 'number') {
      values.ontvangenbedrag = Number(values.ontvangenbedrag);
    }
    if (values.socialreturnbedrag !== undefined && typeof values.socialreturnbedrag !== 'number') {
      values.socialreturnbedrag = Number(values.socialreturnbedrag);
    }
    if (values.subsidiebedrag !== undefined && typeof values.subsidiebedrag !== 'number') {
      values.subsidiebedrag = Number(values.subsidiebedrag);
    }
    if (values.subsidievaststellingbedrag !== undefined && typeof values.subsidievaststellingbedrag !== 'number') {
      values.subsidievaststellingbedrag = Number(values.subsidievaststellingbedrag);
    }

    const entity = {
      ...subsidieEntity,
      ...values,
      heeftZaak: zaaks.find(it => it.id.toString() === values.heeftZaak?.toString()),
      valtbinnenSector: sectors.find(it => it.id.toString() === values.valtbinnenSector?.toString()),
      behandelaarMedewerker: medewerkers.find(it => it.id.toString() === values.behandelaarMedewerker?.toString()),
      verstrekkerRechtspersoon: rechtspersoons.find(it => it.id.toString() === values.verstrekkerRechtspersoon?.toString()),
      heeftKostenplaats: kostenplaats.find(it => it.id.toString() === values.heeftKostenplaats?.toString()),
      heeftDocument: documents.find(it => it.id.toString() === values.heeftDocument?.toString()),
      aanvragerRechtspersoon: rechtspersoons.find(it => it.id.toString() === values.aanvragerRechtspersoon?.toString()),
      aanvragerMedewerker: medewerkers.find(it => it.id.toString() === values.aanvragerMedewerker?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...subsidieEntity,
          heeftZaak: subsidieEntity?.heeftZaak?.id,
          valtbinnenSector: subsidieEntity?.valtbinnenSector?.id,
          behandelaarMedewerker: subsidieEntity?.behandelaarMedewerker?.id,
          verstrekkerRechtspersoon: subsidieEntity?.verstrekkerRechtspersoon?.id,
          heeftKostenplaats: subsidieEntity?.heeftKostenplaats?.id,
          heeftDocument: subsidieEntity?.heeftDocument?.id,
          aanvragerRechtspersoon: subsidieEntity?.aanvragerRechtspersoon?.id,
          aanvragerMedewerker: subsidieEntity?.aanvragerMedewerker?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.subsidie.home.createOrEditLabel" data-cy="SubsidieCreateUpdateHeading">
            Create or edit a Subsidie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="subsidie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Accountantscontrole"
                id="subsidie-accountantscontrole"
                name="accountantscontrole"
                data-cy="accountantscontrole"
                type="text"
              />
              <ValidatedField
                label="Cofinanciering"
                id="subsidie-cofinanciering"
                name="cofinanciering"
                data-cy="cofinanciering"
                type="text"
              />
              <ValidatedField
                label="Datumbehandeltermijn"
                id="subsidie-datumbehandeltermijn"
                name="datumbehandeltermijn"
                data-cy="datumbehandeltermijn"
                type="date"
              />
              <ValidatedField
                label="Datumbewaartermijn"
                id="subsidie-datumbewaartermijn"
                name="datumbewaartermijn"
                data-cy="datumbewaartermijn"
                type="date"
              />
              <ValidatedField label="Datumeinde" id="subsidie-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="subsidie-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField
                label="Datumsubsidievaststelling"
                id="subsidie-datumsubsidievaststelling"
                name="datumsubsidievaststelling"
                data-cy="datumsubsidievaststelling"
                type="date"
              />
              <ValidatedField
                label="Datumverzendingeindeafrekening"
                id="subsidie-datumverzendingeindeafrekening"
                name="datumverzendingeindeafrekening"
                data-cy="datumverzendingeindeafrekening"
                type="date"
              />
              <ValidatedField
                label="Deadlineindiening"
                id="subsidie-deadlineindiening"
                name="deadlineindiening"
                data-cy="deadlineindiening"
                type="date"
              />
              <ValidatedField label="Doelstelling" id="subsidie-doelstelling" name="doelstelling" data-cy="doelstelling" type="text" />
              <ValidatedField
                label="Gerealiseerdeprojectkosten"
                id="subsidie-gerealiseerdeprojectkosten"
                name="gerealiseerdeprojectkosten"
                data-cy="gerealiseerdeprojectkosten"
                type="date"
              />
              <ValidatedField
                label="Hoogtesubsidie"
                id="subsidie-hoogtesubsidie"
                name="hoogtesubsidie"
                data-cy="hoogtesubsidie"
                type="text"
              />
              <ValidatedField label="Niveau" id="subsidie-niveau" name="niveau" data-cy="niveau" type="text" />
              <ValidatedField label="Onderwerp" id="subsidie-onderwerp" name="onderwerp" data-cy="onderwerp" type="text" />
              <ValidatedField
                label="Ontvangenbedrag"
                id="subsidie-ontvangenbedrag"
                name="ontvangenbedrag"
                data-cy="ontvangenbedrag"
                type="text"
              />
              <ValidatedField label="Opmerkingen" id="subsidie-opmerkingen" name="opmerkingen" data-cy="opmerkingen" type="text" />
              <ValidatedField
                label="Opmerkingenvoorschotten"
                id="subsidie-opmerkingenvoorschotten"
                name="opmerkingenvoorschotten"
                data-cy="opmerkingenvoorschotten"
                type="text"
              />
              <ValidatedField
                label="Prestatiesubsidie"
                id="subsidie-prestatiesubsidie"
                name="prestatiesubsidie"
                data-cy="prestatiesubsidie"
                type="text"
              />
              <ValidatedField
                label="Socialreturnbedrag"
                id="subsidie-socialreturnbedrag"
                name="socialreturnbedrag"
                data-cy="socialreturnbedrag"
                type="text"
              />
              <ValidatedField
                label="Socialreturnnagekomen"
                id="subsidie-socialreturnnagekomen"
                name="socialreturnnagekomen"
                data-cy="socialreturnnagekomen"
                type="text"
              />
              <ValidatedField
                label="Socialreturnverplichting"
                id="subsidie-socialreturnverplichting"
                name="socialreturnverplichting"
                data-cy="socialreturnverplichting"
                type="text"
              />
              <ValidatedField label="Status" id="subsidie-status" name="status" data-cy="status" type="text" />
              <ValidatedField
                label="Subsidiebedrag"
                id="subsidie-subsidiebedrag"
                name="subsidiebedrag"
                data-cy="subsidiebedrag"
                type="text"
              />
              <ValidatedField label="Subsidiesoort" id="subsidie-subsidiesoort" name="subsidiesoort" data-cy="subsidiesoort" type="text" />
              <ValidatedField
                label="Subsidievaststellingbedrag"
                id="subsidie-subsidievaststellingbedrag"
                name="subsidievaststellingbedrag"
                data-cy="subsidievaststellingbedrag"
                type="text"
              />
              <ValidatedField
                label="Uitgaandesubsidie"
                id="subsidie-uitgaandesubsidie"
                name="uitgaandesubsidie"
                data-cy="uitgaandesubsidie"
                type="text"
              />
              <ValidatedField
                label="Verantwoordenop"
                id="subsidie-verantwoordenop"
                name="verantwoordenop"
                data-cy="verantwoordenop"
                type="date"
              />
              <ValidatedField id="subsidie-heeftZaak" name="heeftZaak" data-cy="heeftZaak" label="Heeft Zaak" type="select">
                <option value="" key="0" />
                {zaaks
                  ? zaaks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="subsidie-valtbinnenSector"
                name="valtbinnenSector"
                data-cy="valtbinnenSector"
                label="Valtbinnen Sector"
                type="select"
              >
                <option value="" key="0" />
                {sectors
                  ? sectors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="subsidie-behandelaarMedewerker"
                name="behandelaarMedewerker"
                data-cy="behandelaarMedewerker"
                label="Behandelaar Medewerker"
                type="select"
              >
                <option value="" key="0" />
                {medewerkers
                  ? medewerkers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="subsidie-verstrekkerRechtspersoon"
                name="verstrekkerRechtspersoon"
                data-cy="verstrekkerRechtspersoon"
                label="Verstrekker Rechtspersoon"
                type="select"
              >
                <option value="" key="0" />
                {rechtspersoons
                  ? rechtspersoons.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="subsidie-heeftKostenplaats"
                name="heeftKostenplaats"
                data-cy="heeftKostenplaats"
                label="Heeft Kostenplaats"
                type="select"
              >
                <option value="" key="0" />
                {kostenplaats
                  ? kostenplaats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="subsidie-heeftDocument" name="heeftDocument" data-cy="heeftDocument" label="Heeft Document" type="select">
                <option value="" key="0" />
                {documents
                  ? documents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="subsidie-aanvragerRechtspersoon"
                name="aanvragerRechtspersoon"
                data-cy="aanvragerRechtspersoon"
                label="Aanvrager Rechtspersoon"
                type="select"
              >
                <option value="" key="0" />
                {rechtspersoons
                  ? rechtspersoons.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="subsidie-aanvragerMedewerker"
                name="aanvragerMedewerker"
                data-cy="aanvragerMedewerker"
                label="Aanvrager Medewerker"
                type="select"
              >
                <option value="" key="0" />
                {medewerkers
                  ? medewerkers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/subsidie" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SubsidieUpdate;
