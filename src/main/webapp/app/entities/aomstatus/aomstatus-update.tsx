import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAomstatus } from 'app/shared/model/aomstatus.model';
import { getEntity, updateEntity, createEntity, reset } from './aomstatus.reducer';

export const AomstatusUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aomstatusEntity = useAppSelector(state => state.aomstatus.entity);
  const loading = useAppSelector(state => state.aomstatus.loading);
  const updating = useAppSelector(state => state.aomstatus.updating);
  const updateSuccess = useAppSelector(state => state.aomstatus.updateSuccess);

  const handleClose = () => {
    navigate('/aomstatus');
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
      ...aomstatusEntity,
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
          ...aomstatusEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aomstatus.home.createOrEditLabel" data-cy="AomstatusCreateUpdateHeading">
            Create or edit a Aomstatus
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="aomstatus-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumbeginstatus"
                id="aomstatus-datumbeginstatus"
                name="datumbeginstatus"
                data-cy="datumbeginstatus"
                type="date"
              />
              <ValidatedField
                label="Datumeindestatus"
                id="aomstatus-datumeindestatus"
                name="datumeindestatus"
                data-cy="datumeindestatus"
                type="date"
              />
              <ValidatedField label="Status" id="aomstatus-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Statuscode" id="aomstatus-statuscode" name="statuscode" data-cy="statuscode" type="text" />
              <ValidatedField
                label="Statusvolgorde"
                id="aomstatus-statusvolgorde"
                name="statusvolgorde"
                data-cy="statusvolgorde"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aomstatus" replace color="info">
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

export default AomstatusUpdate;
