import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBouwstijl } from 'app/shared/model/bouwstijl.model';
import { getEntity, updateEntity, createEntity, reset } from './bouwstijl.reducer';

export const BouwstijlUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bouwstijlEntity = useAppSelector(state => state.bouwstijl.entity);
  const loading = useAppSelector(state => state.bouwstijl.loading);
  const updating = useAppSelector(state => state.bouwstijl.updating);
  const updateSuccess = useAppSelector(state => state.bouwstijl.updateSuccess);

  const handleClose = () => {
    navigate('/bouwstijl');
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
      ...bouwstijlEntity,
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
          ...bouwstijlEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.bouwstijl.home.createOrEditLabel" data-cy="BouwstijlCreateUpdateHeading">
            Create or edit a Bouwstijl
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="bouwstijl-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Hoofdstijl" id="bouwstijl-hoofdstijl" name="hoofdstijl" data-cy="hoofdstijl" type="text" />
              <ValidatedField label="Substijl" id="bouwstijl-substijl" name="substijl" data-cy="substijl" type="text" />
              <ValidatedField label="Toelichting" id="bouwstijl-toelichting" name="toelichting" data-cy="toelichting" type="text" />
              <ValidatedField label="Zuiverheid" id="bouwstijl-zuiverheid" name="zuiverheid" data-cy="zuiverheid" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bouwstijl" replace color="info">
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

export default BouwstijlUpdate;
