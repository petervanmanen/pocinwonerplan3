import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICultuuronbebouwd } from 'app/shared/model/cultuuronbebouwd.model';
import { getEntity, updateEntity, createEntity, reset } from './cultuuronbebouwd.reducer';

export const CultuuronbebouwdUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cultuuronbebouwdEntity = useAppSelector(state => state.cultuuronbebouwd.entity);
  const loading = useAppSelector(state => state.cultuuronbebouwd.loading);
  const updating = useAppSelector(state => state.cultuuronbebouwd.updating);
  const updateSuccess = useAppSelector(state => state.cultuuronbebouwd.updateSuccess);

  const handleClose = () => {
    navigate('/cultuuronbebouwd');
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
      ...cultuuronbebouwdEntity,
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
          ...cultuuronbebouwdEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.cultuuronbebouwd.home.createOrEditLabel" data-cy="CultuuronbebouwdCreateUpdateHeading">
            Create or edit a Cultuuronbebouwd
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
                <ValidatedField name="id" required readOnly id="cultuuronbebouwd-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Cultuurcodeonbebouwd"
                id="cultuuronbebouwd-cultuurcodeonbebouwd"
                name="cultuurcodeonbebouwd"
                data-cy="cultuurcodeonbebouwd"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cultuuronbebouwd" replace color="info">
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

export default CultuuronbebouwdUpdate;
