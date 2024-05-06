import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAfwijkendbuitenlandscorrespondentieadresrol } from 'app/shared/model/afwijkendbuitenlandscorrespondentieadresrol.model';
import { getEntity, updateEntity, createEntity, reset } from './afwijkendbuitenlandscorrespondentieadresrol.reducer';

export const AfwijkendbuitenlandscorrespondentieadresrolUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const afwijkendbuitenlandscorrespondentieadresrolEntity = useAppSelector(
    state => state.afwijkendbuitenlandscorrespondentieadresrol.entity,
  );
  const loading = useAppSelector(state => state.afwijkendbuitenlandscorrespondentieadresrol.loading);
  const updating = useAppSelector(state => state.afwijkendbuitenlandscorrespondentieadresrol.updating);
  const updateSuccess = useAppSelector(state => state.afwijkendbuitenlandscorrespondentieadresrol.updateSuccess);

  const handleClose = () => {
    navigate('/afwijkendbuitenlandscorrespondentieadresrol');
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
      ...afwijkendbuitenlandscorrespondentieadresrolEntity,
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
          ...afwijkendbuitenlandscorrespondentieadresrolEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.afwijkendbuitenlandscorrespondentieadresrol.home.createOrEditLabel"
            data-cy="AfwijkendbuitenlandscorrespondentieadresrolCreateUpdateHeading"
          >
            Create or edit a Afwijkendbuitenlandscorrespondentieadresrol
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
                  id="afwijkendbuitenlandscorrespondentieadresrol-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label="Adresbuitenland 1"
                id="afwijkendbuitenlandscorrespondentieadresrol-adresbuitenland1"
                name="adresbuitenland1"
                data-cy="adresbuitenland1"
                type="text"
              />
              <ValidatedField
                label="Adresbuitenland 2"
                id="afwijkendbuitenlandscorrespondentieadresrol-adresbuitenland2"
                name="adresbuitenland2"
                data-cy="adresbuitenland2"
                type="text"
              />
              <ValidatedField
                label="Adresbuitenland 3"
                id="afwijkendbuitenlandscorrespondentieadresrol-adresbuitenland3"
                name="adresbuitenland3"
                data-cy="adresbuitenland3"
                type="text"
              />
              <ValidatedField
                label="Landpostadres"
                id="afwijkendbuitenlandscorrespondentieadresrol-landpostadres"
                name="landpostadres"
                data-cy="landpostadres"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/afwijkendbuitenlandscorrespondentieadresrol"
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

export default AfwijkendbuitenlandscorrespondentieadresrolUpdate;
