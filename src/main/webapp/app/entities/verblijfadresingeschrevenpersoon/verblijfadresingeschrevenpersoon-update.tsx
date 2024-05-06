import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerblijfadresingeschrevenpersoon } from 'app/shared/model/verblijfadresingeschrevenpersoon.model';
import { getEntity, updateEntity, createEntity, reset } from './verblijfadresingeschrevenpersoon.reducer';

export const VerblijfadresingeschrevenpersoonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verblijfadresingeschrevenpersoonEntity = useAppSelector(state => state.verblijfadresingeschrevenpersoon.entity);
  const loading = useAppSelector(state => state.verblijfadresingeschrevenpersoon.loading);
  const updating = useAppSelector(state => state.verblijfadresingeschrevenpersoon.updating);
  const updateSuccess = useAppSelector(state => state.verblijfadresingeschrevenpersoon.updateSuccess);

  const handleClose = () => {
    navigate('/verblijfadresingeschrevenpersoon');
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
      ...verblijfadresingeschrevenpersoonEntity,
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
          ...verblijfadresingeschrevenpersoonEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.verblijfadresingeschrevenpersoon.home.createOrEditLabel"
            data-cy="VerblijfadresingeschrevenpersoonCreateUpdateHeading"
          >
            Create or edit a Verblijfadresingeschrevenpersoon
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
                  id="verblijfadresingeschrevenpersoon-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Adresherkomst"
                id="verblijfadresingeschrevenpersoon-adresherkomst"
                name="adresherkomst"
                data-cy="adresherkomst"
                type="text"
              />
              <ValidatedField
                label="Beschrijvinglocatie"
                id="verblijfadresingeschrevenpersoon-beschrijvinglocatie"
                name="beschrijvinglocatie"
                data-cy="beschrijvinglocatie"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/verblijfadresingeschrevenpersoon"
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

export default VerblijfadresingeschrevenpersoonUpdate;
