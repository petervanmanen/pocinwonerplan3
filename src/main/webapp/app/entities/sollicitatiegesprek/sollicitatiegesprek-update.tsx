import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISollicitatie } from 'app/shared/model/sollicitatie.model';
import { getEntities as getSollicitaties } from 'app/entities/sollicitatie/sollicitatie.reducer';
import { ISollicitant } from 'app/shared/model/sollicitant.model';
import { getEntities as getSollicitants } from 'app/entities/sollicitant/sollicitant.reducer';
import { IWerknemer } from 'app/shared/model/werknemer.model';
import { getEntities as getWerknemers } from 'app/entities/werknemer/werknemer.reducer';
import { ISollicitatiegesprek } from 'app/shared/model/sollicitatiegesprek.model';
import { getEntity, updateEntity, createEntity, reset } from './sollicitatiegesprek.reducer';

export const SollicitatiegesprekUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sollicitaties = useAppSelector(state => state.sollicitatie.entities);
  const sollicitants = useAppSelector(state => state.sollicitant.entities);
  const werknemers = useAppSelector(state => state.werknemer.entities);
  const sollicitatiegesprekEntity = useAppSelector(state => state.sollicitatiegesprek.entity);
  const loading = useAppSelector(state => state.sollicitatiegesprek.loading);
  const updating = useAppSelector(state => state.sollicitatiegesprek.updating);
  const updateSuccess = useAppSelector(state => state.sollicitatiegesprek.updateSuccess);

  const handleClose = () => {
    navigate('/sollicitatiegesprek');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSollicitaties({}));
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
      ...sollicitatiegesprekEntity,
      ...values,
      inkadervanSollicitatie: sollicitaties.find(it => it.id.toString() === values.inkadervanSollicitatie?.toString()),
      kandidaatSollicitants: mapIdList(values.kandidaatSollicitants),
      doetsollicitatiegesprekWerknemers: mapIdList(values.doetsollicitatiegesprekWerknemers),
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
          ...sollicitatiegesprekEntity,
          inkadervanSollicitatie: sollicitatiegesprekEntity?.inkadervanSollicitatie?.id,
          kandidaatSollicitants: sollicitatiegesprekEntity?.kandidaatSollicitants?.map(e => e.id.toString()),
          doetsollicitatiegesprekWerknemers: sollicitatiegesprekEntity?.doetsollicitatiegesprekWerknemers?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.sollicitatiegesprek.home.createOrEditLabel" data-cy="SollicitatiegesprekCreateUpdateHeading">
            Create or edit a Sollicitatiegesprek
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="sollicitatiegesprek-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aangenomen"
                id="sollicitatiegesprek-aangenomen"
                name="aangenomen"
                data-cy="aangenomen"
                check
                type="checkbox"
              />
              <ValidatedField label="Datum" id="sollicitatiegesprek-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField
                label="Opmerkingen"
                id="sollicitatiegesprek-opmerkingen"
                name="opmerkingen"
                data-cy="opmerkingen"
                type="text"
              />
              <ValidatedField
                label="Volgendgesprek"
                id="sollicitatiegesprek-volgendgesprek"
                name="volgendgesprek"
                data-cy="volgendgesprek"
                check
                type="checkbox"
              />
              <ValidatedField
                id="sollicitatiegesprek-inkadervanSollicitatie"
                name="inkadervanSollicitatie"
                data-cy="inkadervanSollicitatie"
                label="Inkadervan Sollicitatie"
                type="select"
              >
                <option value="" key="0" />
                {sollicitaties
                  ? sollicitaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Kandidaat Sollicitant"
                id="sollicitatiegesprek-kandidaatSollicitant"
                data-cy="kandidaatSollicitant"
                type="select"
                multiple
                name="kandidaatSollicitants"
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
                label="Doetsollicitatiegesprek Werknemer"
                id="sollicitatiegesprek-doetsollicitatiegesprekWerknemer"
                data-cy="doetsollicitatiegesprekWerknemer"
                type="select"
                multiple
                name="doetsollicitatiegesprekWerknemers"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sollicitatiegesprek" replace color="info">
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

export default SollicitatiegesprekUpdate;
