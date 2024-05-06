import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVacature } from 'app/shared/model/vacature.model';
import { getEntities as getVacatures } from 'app/entities/vacature/vacature.reducer';
import { ISollicitant } from 'app/shared/model/sollicitant.model';
import { getEntities as getSollicitants } from 'app/entities/sollicitant/sollicitant.reducer';
import { IWerknemer } from 'app/shared/model/werknemer.model';
import { getEntities as getWerknemers } from 'app/entities/werknemer/werknemer.reducer';
import { ISollicitatie } from 'app/shared/model/sollicitatie.model';
import { getEntity, updateEntity, createEntity, reset } from './sollicitatie.reducer';

export const SollicitatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vacatures = useAppSelector(state => state.vacature.entities);
  const sollicitants = useAppSelector(state => state.sollicitant.entities);
  const werknemers = useAppSelector(state => state.werknemer.entities);
  const sollicitatieEntity = useAppSelector(state => state.sollicitatie.entity);
  const loading = useAppSelector(state => state.sollicitatie.loading);
  const updating = useAppSelector(state => state.sollicitatie.updating);
  const updateSuccess = useAppSelector(state => state.sollicitatie.updateSuccess);

  const handleClose = () => {
    navigate('/sollicitatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVacatures({}));
    dispatch(getSollicitants({}));
    dispatch(getWerknemers({}));
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
      ...sollicitatieEntity,
      ...values,
      opvacatureVacature: vacatures.find(it => it.id.toString() === values.opvacatureVacature?.toString()),
      solliciteertopfunctieSollicitant: sollicitants.find(it => it.id.toString() === values.solliciteertopfunctieSollicitant?.toString()),
      solliciteertWerknemer: werknemers.find(it => it.id.toString() === values.solliciteertWerknemer?.toString()),
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
          ...sollicitatieEntity,
          opvacatureVacature: sollicitatieEntity?.opvacatureVacature?.id,
          solliciteertopfunctieSollicitant: sollicitatieEntity?.solliciteertopfunctieSollicitant?.id,
          solliciteertWerknemer: sollicitatieEntity?.solliciteertWerknemer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.sollicitatie.home.createOrEditLabel" data-cy="SollicitatieCreateUpdateHeading">
            Create or edit a Sollicitatie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="sollicitatie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datum" id="sollicitatie-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField
                id="sollicitatie-opvacatureVacature"
                name="opvacatureVacature"
                data-cy="opvacatureVacature"
                label="Opvacature Vacature"
                type="select"
              >
                <option value="" key="0" />
                {vacatures
                  ? vacatures.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="sollicitatie-solliciteertopfunctieSollicitant"
                name="solliciteertopfunctieSollicitant"
                data-cy="solliciteertopfunctieSollicitant"
                label="Solliciteertopfunctie Sollicitant"
                type="select"
              >
                <option value="" key="0" />
                {sollicitants
                  ? sollicitants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="sollicitatie-solliciteertWerknemer"
                name="solliciteertWerknemer"
                data-cy="solliciteertWerknemer"
                label="Solliciteert Werknemer"
                type="select"
              >
                <option value="" key="0" />
                {werknemers
                  ? werknemers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sollicitatie" replace color="info">
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

export default SollicitatieUpdate;
