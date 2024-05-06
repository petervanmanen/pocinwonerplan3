import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITelefoononderwerp } from 'app/shared/model/telefoononderwerp.model';
import { getEntity, updateEntity, createEntity, reset } from './telefoononderwerp.reducer';

export const TelefoononderwerpUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const telefoononderwerpEntity = useAppSelector(state => state.telefoononderwerp.entity);
  const loading = useAppSelector(state => state.telefoononderwerp.loading);
  const updating = useAppSelector(state => state.telefoononderwerp.updating);
  const updateSuccess = useAppSelector(state => state.telefoononderwerp.updateSuccess);

  const handleClose = () => {
    navigate('/telefoononderwerp');
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
      ...telefoononderwerpEntity,
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
          ...telefoononderwerpEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.telefoononderwerp.home.createOrEditLabel" data-cy="TelefoononderwerpCreateUpdateHeading">
            Create or edit a Telefoononderwerp
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
                <ValidatedField name="id" required readOnly id="telefoononderwerp-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Onderwerp" id="telefoononderwerp-onderwerp" name="onderwerp" data-cy="onderwerp" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/telefoononderwerp" replace color="info">
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

export default TelefoononderwerpUpdate;
