import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INotarielestatus } from 'app/shared/model/notarielestatus.model';
import { getEntity, updateEntity, createEntity, reset } from './notarielestatus.reducer';

export const NotarielestatusUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const notarielestatusEntity = useAppSelector(state => state.notarielestatus.entity);
  const loading = useAppSelector(state => state.notarielestatus.loading);
  const updating = useAppSelector(state => state.notarielestatus.updating);
  const updateSuccess = useAppSelector(state => state.notarielestatus.updateSuccess);

  const handleClose = () => {
    navigate('/notarielestatus');
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
      ...notarielestatusEntity,
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
          ...notarielestatusEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.notarielestatus.home.createOrEditLabel" data-cy="NotarielestatusCreateUpdateHeading">
            Create or edit a Notarielestatus
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
                <ValidatedField name="id" required readOnly id="notarielestatus-id" label="ID" validate={{ required: true }} />
              ) : null}
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/notarielestatus" replace color="info">
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

export default NotarielestatusUpdate;
