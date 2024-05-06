import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProcesverbaalonderwijs } from 'app/shared/model/procesverbaalonderwijs.model';
import { getEntity, updateEntity, createEntity, reset } from './procesverbaalonderwijs.reducer';

export const ProcesverbaalonderwijsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const procesverbaalonderwijsEntity = useAppSelector(state => state.procesverbaalonderwijs.entity);
  const loading = useAppSelector(state => state.procesverbaalonderwijs.loading);
  const updating = useAppSelector(state => state.procesverbaalonderwijs.updating);
  const updateSuccess = useAppSelector(state => state.procesverbaalonderwijs.updateSuccess);

  const handleClose = () => {
    navigate('/procesverbaalonderwijs');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
    if (values.geldboete !== undefined && typeof values.geldboete !== 'number') {
      values.geldboete = Number(values.geldboete);
    }

    const entity = {
      ...procesverbaalonderwijsEntity,
      ...values,
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
          ...procesverbaalonderwijsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.procesverbaalonderwijs.home.createOrEditLabel" data-cy="ProcesverbaalonderwijsCreateUpdateHeading">
            Create or edit a Procesverbaalonderwijs
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
                <ValidatedField name="id" required readOnly id="procesverbaalonderwijs-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumafgehandeld"
                id="procesverbaalonderwijs-datumafgehandeld"
                name="datumafgehandeld"
                data-cy="datumafgehandeld"
                type="date"
              />
              <ValidatedField
                label="Datumeindeproeftijd"
                id="procesverbaalonderwijs-datumeindeproeftijd"
                name="datumeindeproeftijd"
                data-cy="datumeindeproeftijd"
                type="date"
              />
              <ValidatedField
                label="Datumingelicht"
                id="procesverbaalonderwijs-datumingelicht"
                name="datumingelicht"
                data-cy="datumingelicht"
                type="date"
              />
              <ValidatedField
                label="Datumuitspraak"
                id="procesverbaalonderwijs-datumuitspraak"
                name="datumuitspraak"
                data-cy="datumuitspraak"
                type="date"
              />
              <ValidatedField
                label="Datumzitting"
                id="procesverbaalonderwijs-datumzitting"
                name="datumzitting"
                data-cy="datumzitting"
                type="date"
              />
              <ValidatedField label="Geldboete" id="procesverbaalonderwijs-geldboete" name="geldboete" data-cy="geldboete" type="text" />
              <ValidatedField
                label="Geldboetevoorwaardelijk"
                id="procesverbaalonderwijs-geldboetevoorwaardelijk"
                name="geldboetevoorwaardelijk"
                data-cy="geldboetevoorwaardelijk"
                type="text"
              />
              <ValidatedField
                label="Opmerkingen"
                id="procesverbaalonderwijs-opmerkingen"
                name="opmerkingen"
                data-cy="opmerkingen"
                type="text"
              />
              <ValidatedField label="Proeftijd" id="procesverbaalonderwijs-proeftijd" name="proeftijd" data-cy="proeftijd" type="text" />
              <ValidatedField label="Reden" id="procesverbaalonderwijs-reden" name="reden" data-cy="reden" type="text" />
              <ValidatedField
                label="Sanctiesoort"
                id="procesverbaalonderwijs-sanctiesoort"
                name="sanctiesoort"
                data-cy="sanctiesoort"
                type="text"
              />
              <ValidatedField label="Uitspraak" id="procesverbaalonderwijs-uitspraak" name="uitspraak" data-cy="uitspraak" type="text" />
              <ValidatedField
                label="Verzuimsoort"
                id="procesverbaalonderwijs-verzuimsoort"
                name="verzuimsoort"
                data-cy="verzuimsoort"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/procesverbaalonderwijs" replace color="info">
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

export default ProcesverbaalonderwijsUpdate;
