import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRioolput } from 'app/shared/model/rioolput.model';
import { getEntity, updateEntity, createEntity, reset } from './rioolput.reducer';

export const RioolputUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rioolputEntity = useAppSelector(state => state.rioolput.entity);
  const loading = useAppSelector(state => state.rioolput.loading);
  const updating = useAppSelector(state => state.rioolput.updating);
  const updateSuccess = useAppSelector(state => state.rioolput.updateSuccess);

  const handleClose = () => {
    navigate('/rioolput');
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

    const entity = {
      ...rioolputEntity,
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
          ...rioolputEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.rioolput.home.createOrEditLabel" data-cy="RioolputCreateUpdateHeading">
            Create or edit a Rioolput
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="rioolput-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aantalbedrijven"
                id="rioolput-aantalbedrijven"
                name="aantalbedrijven"
                data-cy="aantalbedrijven"
                type="text"
              />
              <ValidatedField
                label="Aantalrecreatie"
                id="rioolput-aantalrecreatie"
                name="aantalrecreatie"
                data-cy="aantalrecreatie"
                type="text"
              />
              <ValidatedField
                label="Aantalwoningen"
                id="rioolput-aantalwoningen"
                name="aantalwoningen"
                data-cy="aantalwoningen"
                type="text"
              />
              <ValidatedField
                label="Afvoerendoppervlak"
                id="rioolput-afvoerendoppervlak"
                name="afvoerendoppervlak"
                data-cy="afvoerendoppervlak"
                type="text"
              />
              <ValidatedField
                label="Bergendoppervlak"
                id="rioolput-bergendoppervlak"
                name="bergendoppervlak"
                data-cy="bergendoppervlak"
                type="text"
              />
              <ValidatedField
                label="Rioolputconstructieonderdeel"
                id="rioolput-rioolputconstructieonderdeel"
                name="rioolputconstructieonderdeel"
                data-cy="rioolputconstructieonderdeel"
                type="text"
              />
              <ValidatedField
                label="Rioolputrioolleiding"
                id="rioolput-rioolputrioolleiding"
                name="rioolputrioolleiding"
                data-cy="rioolputrioolleiding"
                type="text"
              />
              <ValidatedField label="Risicogebied" id="rioolput-risicogebied" name="risicogebied" data-cy="risicogebied" type="text" />
              <ValidatedField
                label="Toegangbreedte"
                id="rioolput-toegangbreedte"
                name="toegangbreedte"
                data-cy="toegangbreedte"
                type="text"
              />
              <ValidatedField label="Toeganglengte" id="rioolput-toeganglengte" name="toeganglengte" data-cy="toeganglengte" type="text" />
              <ValidatedField label="Type" id="rioolput-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Typeplus" id="rioolput-typeplus" name="typeplus" data-cy="typeplus" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rioolput" replace color="info">
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

export default RioolputUpdate;
