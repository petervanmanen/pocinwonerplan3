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
import { IGunning } from 'app/shared/model/gunning.model';
import { getEntities as getGunnings } from 'app/entities/gunning/gunning.reducer';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { getEntities as getMedewerkers } from 'app/entities/medewerker/medewerker.reducer';
import { IAanbesteding } from 'app/shared/model/aanbesteding.model';
import { getEntity, updateEntity, createEntity, reset } from './aanbesteding.reducer';

export const AanbestedingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const zaaks = useAppSelector(state => state.zaak.entities);
  const gunnings = useAppSelector(state => state.gunning.entities);
  const medewerkers = useAppSelector(state => state.medewerker.entities);
  const aanbestedingEntity = useAppSelector(state => state.aanbesteding.entity);
  const loading = useAppSelector(state => state.aanbesteding.loading);
  const updating = useAppSelector(state => state.aanbesteding.updating);
  const updateSuccess = useAppSelector(state => state.aanbesteding.updateSuccess);

  const handleClose = () => {
    navigate('/aanbesteding');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getZaaks({}));
    dispatch(getGunnings({}));
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
      ...aanbestedingEntity,
      ...values,
      betreftZaak: zaaks.find(it => it.id.toString() === values.betreftZaak?.toString()),
      mondtuitGunning: gunnings.find(it => it.id.toString() === values.mondtuitGunning?.toString()),
      procesleiderMedewerker: medewerkers.find(it => it.id.toString() === values.procesleiderMedewerker?.toString()),
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
          ...aanbestedingEntity,
          betreftZaak: aanbestedingEntity?.betreftZaak?.id,
          mondtuitGunning: aanbestedingEntity?.mondtuitGunning?.id,
          procesleiderMedewerker: aanbestedingEntity?.procesleiderMedewerker?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aanbesteding.home.createOrEditLabel" data-cy="AanbestedingCreateUpdateHeading">
            Create or edit a Aanbesteding
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="aanbesteding-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumpublicatie"
                id="aanbesteding-datumpublicatie"
                name="datumpublicatie"
                data-cy="datumpublicatie"
                type="text"
              />
              <ValidatedField label="Datumstart" id="aanbesteding-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Digitaal" id="aanbesteding-digitaal" name="digitaal" data-cy="digitaal" type="text" />
              <ValidatedField label="Naam" id="aanbesteding-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Procedure" id="aanbesteding-procedure" name="procedure" data-cy="procedure" type="text" />
              <ValidatedField
                label="Referentienummer"
                id="aanbesteding-referentienummer"
                name="referentienummer"
                data-cy="referentienummer"
                type="text"
              />
              <ValidatedField
                label="Scoremaximaal"
                id="aanbesteding-scoremaximaal"
                name="scoremaximaal"
                data-cy="scoremaximaal"
                type="text"
              />
              <ValidatedField label="Status" id="aanbesteding-status" name="status" data-cy="status" type="text" />
              <ValidatedField
                label="Tendernedkenmerk"
                id="aanbesteding-tendernedkenmerk"
                name="tendernedkenmerk"
                data-cy="tendernedkenmerk"
                type="text"
              />
              <ValidatedField label="Type" id="aanbesteding-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label="Volgendesluiting"
                id="aanbesteding-volgendesluiting"
                name="volgendesluiting"
                data-cy="volgendesluiting"
                type="text"
              />
              <ValidatedField id="aanbesteding-betreftZaak" name="betreftZaak" data-cy="betreftZaak" label="Betreft Zaak" type="select">
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
                id="aanbesteding-mondtuitGunning"
                name="mondtuitGunning"
                data-cy="mondtuitGunning"
                label="Mondtuit Gunning"
                type="select"
              >
                <option value="" key="0" />
                {gunnings
                  ? gunnings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="aanbesteding-procesleiderMedewerker"
                name="procesleiderMedewerker"
                data-cy="procesleiderMedewerker"
                label="Procesleider Medewerker"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aanbesteding" replace color="info">
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

export default AanbestedingUpdate;
