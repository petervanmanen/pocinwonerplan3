import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerblijfadresingeschrevennatuurlijkpersoon } from 'app/shared/model/verblijfadresingeschrevennatuurlijkpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './verblijfadresingeschrevennatuurlijkpersoon.reducer';

export const VerblijfadresingeschrevennatuurlijkpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verblijfadresingeschrevennatuurlijkpersoonEntity = useAppSelector(state => state.verblijfadresingeschrevennatuurlijkpersoon.entity);
  const loading = useAppSelector(state => state.verblijfadresingeschrevennatuurlijkpersoon.loading);
  const updating = useAppSelector(state => state.verblijfadresingeschrevennatuurlijkpersoon.updating);
  const updateSuccess = useAppSelector(state => state.verblijfadresingeschrevennatuurlijkpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/verblijfadresingeschrevennatuurlijkpersoon');
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
      ...verblijfadresingeschrevennatuurlijkpersoonEntity,
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
          ...verblijfadresingeschrevennatuurlijkpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.verblijfadresingeschrevennatuurlijkpersoon.home.createOrEditLabel"
            data-cy="VerblijfadresingeschrevennatuurlijkpersoonCreateUpdateHeading"
          >
            Create or edit a Verblijfadresingeschrevennatuurlijkpersoon
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
                  id="verblijfadresingeschrevennatuurlijkpersoon-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Adresherkomst"
                id="verblijfadresingeschrevennatuurlijkpersoon-adresherkomst"
                name="adresherkomst"
                data-cy="adresherkomst"
                type="text"
              />
              <ValidatedField
                label="Beschrijvinglocatie"
                id="verblijfadresingeschrevennatuurlijkpersoon-beschrijvinglocatie"
                name="beschrijvinglocatie"
                data-cy="beschrijvinglocatie"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/verblijfadresingeschrevennatuurlijkpersoon"
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

export default VerblijfadresingeschrevennatuurlijkpersoonUpdate;
