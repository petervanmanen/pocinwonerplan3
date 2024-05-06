import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInschrijving } from 'app/shared/model/inschrijving.model';
import { getEntities as getInschrijvings } from 'app/entities/inschrijving/inschrijving.reducer';
import { IKandidaat } from 'app/shared/model/kandidaat.model';
import { getEntities as getKandidaats } from 'app/entities/kandidaat/kandidaat.reducer';
import { IOfferte } from 'app/shared/model/offerte.model';
import { getEntities as getOffertes } from 'app/entities/offerte/offerte.reducer';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { getEntities as getMedewerkers } from 'app/entities/medewerker/medewerker.reducer';
import { IGunning } from 'app/shared/model/gunning.model';
import { getEntity, updateEntity, createEntity, reset } from './gunning.reducer';

export const GunningUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const inschrijvings = useAppSelector(state => state.inschrijving.entities);
  const kandidaats = useAppSelector(state => state.kandidaat.entities);
  const offertes = useAppSelector(state => state.offerte.entities);
  const medewerkers = useAppSelector(state => state.medewerker.entities);
  const gunningEntity = useAppSelector(state => state.gunning.entity);
  const loading = useAppSelector(state => state.gunning.loading);
  const updating = useAppSelector(state => state.gunning.updating);
  const updateSuccess = useAppSelector(state => state.gunning.updateSuccess);

  const handleClose = () => {
    navigate('/gunning');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getInschrijvings({}));
    dispatch(getKandidaats({}));
    dispatch(getOffertes({}));
    dispatch(getMedewerkers({}));
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

    const entity = {
      ...gunningEntity,
      ...values,
      betreftInschrijving: inschrijvings.find(it => it.id.toString() === values.betreftInschrijving?.toString()),
      betreftKandidaat: kandidaats.find(it => it.id.toString() === values.betreftKandidaat?.toString()),
      betreftOfferte: offertes.find(it => it.id.toString() === values.betreftOfferte?.toString()),
      inhuurMedewerker: medewerkers.find(it => it.id.toString() === values.inhuurMedewerker?.toString()),
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
          ...gunningEntity,
          betreftInschrijving: gunningEntity?.betreftInschrijving?.id,
          betreftKandidaat: gunningEntity?.betreftKandidaat?.id,
          betreftOfferte: gunningEntity?.betreftOfferte?.id,
          inhuurMedewerker: gunningEntity?.inhuurMedewerker?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.gunning.home.createOrEditLabel" data-cy="GunningCreateUpdateHeading">
            Create or edit a Gunning
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="gunning-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Bericht" id="gunning-bericht" name="bericht" data-cy="bericht" type="text" />
              <ValidatedField label="Datumgunning" id="gunning-datumgunning" name="datumgunning" data-cy="datumgunning" type="text" />
              <ValidatedField
                label="Datumpublicatie"
                id="gunning-datumpublicatie"
                name="datumpublicatie"
                data-cy="datumpublicatie"
                type="text"
              />
              <ValidatedField
                label="Datumvoorlopigegunning"
                id="gunning-datumvoorlopigegunning"
                name="datumvoorlopigegunning"
                data-cy="datumvoorlopigegunning"
                type="text"
              />
              <ValidatedField label="Gegundeprijs" id="gunning-gegundeprijs" name="gegundeprijs" data-cy="gegundeprijs" type="text" />
              <ValidatedField
                id="gunning-betreftInschrijving"
                name="betreftInschrijving"
                data-cy="betreftInschrijving"
                label="Betreft Inschrijving"
                type="select"
              >
                <option value="" key="0" />
                {inschrijvings
                  ? inschrijvings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="gunning-betreftKandidaat"
                name="betreftKandidaat"
                data-cy="betreftKandidaat"
                label="Betreft Kandidaat"
                type="select"
              >
                <option value="" key="0" />
                {kandidaats
                  ? kandidaats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="gunning-betreftOfferte"
                name="betreftOfferte"
                data-cy="betreftOfferte"
                label="Betreft Offerte"
                type="select"
              >
                <option value="" key="0" />
                {offertes
                  ? offertes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="gunning-inhuurMedewerker"
                name="inhuurMedewerker"
                data-cy="inhuurMedewerker"
                label="Inhuur Medewerker"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gunning" replace color="info">
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

export default GunningUpdate;
