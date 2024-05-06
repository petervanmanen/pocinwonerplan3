import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INaheffing } from 'app/shared/model/naheffing.model';
import { getEntities as getNaheffings } from 'app/entities/naheffing/naheffing.reducer';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { getEntities as getMedewerkers } from 'app/entities/medewerker/medewerker.reducer';
import { IVoertuig } from 'app/shared/model/voertuig.model';
import { getEntities as getVoertuigs } from 'app/entities/voertuig/voertuig.reducer';
import { IParkeervlak } from 'app/shared/model/parkeervlak.model';
import { getEntities as getParkeervlaks } from 'app/entities/parkeervlak/parkeervlak.reducer';
import { IParkeerscan } from 'app/shared/model/parkeerscan.model';
import { getEntity, updateEntity, createEntity, reset } from './parkeerscan.reducer';

export const ParkeerscanUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const naheffings = useAppSelector(state => state.naheffing.entities);
  const medewerkers = useAppSelector(state => state.medewerker.entities);
  const voertuigs = useAppSelector(state => state.voertuig.entities);
  const parkeervlaks = useAppSelector(state => state.parkeervlak.entities);
  const parkeerscanEntity = useAppSelector(state => state.parkeerscan.entity);
  const loading = useAppSelector(state => state.parkeerscan.loading);
  const updating = useAppSelector(state => state.parkeerscan.updating);
  const updateSuccess = useAppSelector(state => state.parkeerscan.updateSuccess);

  const handleClose = () => {
    navigate('/parkeerscan');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getNaheffings({}));
    dispatch(getMedewerkers({}));
    dispatch(getVoertuigs({}));
    dispatch(getParkeervlaks({}));
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
      ...parkeerscanEntity,
      ...values,
      komtvoortuitNaheffing: naheffings.find(it => it.id.toString() === values.komtvoortuitNaheffing?.toString()),
      uitgevoerddoorMedewerker: medewerkers.find(it => it.id.toString() === values.uitgevoerddoorMedewerker?.toString()),
      betreftVoertuig: voertuigs.find(it => it.id.toString() === values.betreftVoertuig?.toString()),
      betreftParkeervlak: parkeervlaks.find(it => it.id.toString() === values.betreftParkeervlak?.toString()),
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
          ...parkeerscanEntity,
          komtvoortuitNaheffing: parkeerscanEntity?.komtvoortuitNaheffing?.id,
          uitgevoerddoorMedewerker: parkeerscanEntity?.uitgevoerddoorMedewerker?.id,
          betreftVoertuig: parkeerscanEntity?.betreftVoertuig?.id,
          betreftParkeervlak: parkeerscanEntity?.betreftParkeervlak?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.parkeerscan.home.createOrEditLabel" data-cy="ParkeerscanCreateUpdateHeading">
            Create or edit a Parkeerscan
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="parkeerscan-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Codegebruiker"
                id="parkeerscan-codegebruiker"
                name="codegebruiker"
                data-cy="codegebruiker"
                type="text"
              />
              <ValidatedField
                label="Codescanvoertuig"
                id="parkeerscan-codescanvoertuig"
                name="codescanvoertuig"
                data-cy="codescanvoertuig"
                type="text"
              />
              <ValidatedField label="Coordinaten" id="parkeerscan-coordinaten" name="coordinaten" data-cy="coordinaten" type="text" />
              <ValidatedField label="Foto" id="parkeerscan-foto" name="foto" data-cy="foto" type="text" />
              <ValidatedField label="Kenteken" id="parkeerscan-kenteken" name="kenteken" data-cy="kenteken" type="text" />
              <ValidatedField
                label="Parkeerrecht"
                id="parkeerscan-parkeerrecht"
                name="parkeerrecht"
                data-cy="parkeerrecht"
                check
                type="checkbox"
              />
              <ValidatedField label="Tijdstip" id="parkeerscan-tijdstip" name="tijdstip" data-cy="tijdstip" type="text" />
              <ValidatedField label="Transactieid" id="parkeerscan-transactieid" name="transactieid" data-cy="transactieid" type="text" />
              <ValidatedField
                id="parkeerscan-komtvoortuitNaheffing"
                name="komtvoortuitNaheffing"
                data-cy="komtvoortuitNaheffing"
                label="Komtvoortuit Naheffing"
                type="select"
              >
                <option value="" key="0" />
                {naheffings
                  ? naheffings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="parkeerscan-uitgevoerddoorMedewerker"
                name="uitgevoerddoorMedewerker"
                data-cy="uitgevoerddoorMedewerker"
                label="Uitgevoerddoor Medewerker"
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
                id="parkeerscan-betreftVoertuig"
                name="betreftVoertuig"
                data-cy="betreftVoertuig"
                label="Betreft Voertuig"
                type="select"
              >
                <option value="" key="0" />
                {voertuigs
                  ? voertuigs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="parkeerscan-betreftParkeervlak"
                name="betreftParkeervlak"
                data-cy="betreftParkeervlak"
                label="Betreft Parkeervlak"
                type="select"
              >
                <option value="" key="0" />
                {parkeervlaks
                  ? parkeervlaks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/parkeerscan" replace color="info">
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

export default ParkeerscanUpdate;
