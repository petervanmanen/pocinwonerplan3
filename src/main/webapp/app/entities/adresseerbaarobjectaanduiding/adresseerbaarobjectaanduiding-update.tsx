import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAdresseerbaarobjectaanduiding } from 'app/shared/model/adresseerbaarobjectaanduiding.model';
import { getEntity, updateEntity, createEntity, reset } from './adresseerbaarobjectaanduiding.reducer';

export const AdresseerbaarobjectaanduidingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const adresseerbaarobjectaanduidingEntity = useAppSelector(state => state.adresseerbaarobjectaanduiding.entity);
  const loading = useAppSelector(state => state.adresseerbaarobjectaanduiding.loading);
  const updating = useAppSelector(state => state.adresseerbaarobjectaanduiding.updating);
  const updateSuccess = useAppSelector(state => state.adresseerbaarobjectaanduiding.updateSuccess);

  const handleClose = () => {
    navigate('/adresseerbaarobjectaanduiding');
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
      ...adresseerbaarobjectaanduidingEntity,
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
          ...adresseerbaarobjectaanduidingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.adresseerbaarobjectaanduiding.home.createOrEditLabel" data-cy="AdresseerbaarobjectaanduidingCreateUpdateHeading">
            Create or edit a Adresseerbaarobjectaanduiding
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="adresseerbaarobjectaanduiding-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Identificatie"
                id="adresseerbaarobjectaanduiding-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/adresseerbaarobjectaanduiding"
                replace
                color="info"
              >
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

export default AdresseerbaarobjectaanduidingUpdate;
