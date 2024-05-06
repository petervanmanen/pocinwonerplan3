import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAdresseerbaarobject } from 'app/shared/model/adresseerbaarobject.model';
import { getEntity, updateEntity, createEntity, reset } from './adresseerbaarobject.reducer';

export const AdresseerbaarobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const adresseerbaarobjectEntity = useAppSelector(state => state.adresseerbaarobject.entity);
  const loading = useAppSelector(state => state.adresseerbaarobject.loading);
  const updating = useAppSelector(state => state.adresseerbaarobject.updating);
  const updateSuccess = useAppSelector(state => state.adresseerbaarobject.updateSuccess);

  const handleClose = () => {
    navigate('/adresseerbaarobject');
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
      ...adresseerbaarobjectEntity,
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
          ...adresseerbaarobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.adresseerbaarobject.home.createOrEditLabel" data-cy="AdresseerbaarobjectCreateUpdateHeading">
            Create or edit a Adresseerbaarobject
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
                <ValidatedField name="id" required readOnly id="adresseerbaarobject-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Identificatie"
                id="adresseerbaarobject-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
              />
              <ValidatedField
                label="Typeadresseerbaarobject"
                id="adresseerbaarobject-typeadresseerbaarobject"
                name="typeadresseerbaarobject"
                data-cy="typeadresseerbaarobject"
                type="text"
              />
              <ValidatedField label="Versie" id="adresseerbaarobject-versie" name="versie" data-cy="versie" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/adresseerbaarobject" replace color="info">
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

export default AdresseerbaarobjectUpdate;
