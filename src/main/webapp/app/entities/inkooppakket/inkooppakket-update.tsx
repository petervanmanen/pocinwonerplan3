import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInkooppakket } from 'app/shared/model/inkooppakket.model';
import { getEntity, updateEntity, createEntity, reset } from './inkooppakket.reducer';

export const InkooppakketUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const inkooppakketEntity = useAppSelector(state => state.inkooppakket.entity);
  const loading = useAppSelector(state => state.inkooppakket.loading);
  const updating = useAppSelector(state => state.inkooppakket.updating);
  const updateSuccess = useAppSelector(state => state.inkooppakket.updateSuccess);

  const handleClose = () => {
    navigate('/inkooppakket');
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
      ...inkooppakketEntity,
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
          ...inkooppakketEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.inkooppakket.home.createOrEditLabel" data-cy="InkooppakketCreateUpdateHeading">
            Create or edit a Inkooppakket
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="inkooppakket-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Code" id="inkooppakket-code" name="code" data-cy="code" type="text" />
              <ValidatedField label="Naam" id="inkooppakket-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Type" id="inkooppakket-type" name="type" data-cy="type" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inkooppakket" replace color="info">
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

export default InkooppakketUpdate;
