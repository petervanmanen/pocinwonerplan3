import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOveriggebouwdobject } from 'app/shared/model/overiggebouwdobject.model';
import { getEntity, updateEntity, createEntity, reset } from './overiggebouwdobject.reducer';

export const OveriggebouwdobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const overiggebouwdobjectEntity = useAppSelector(state => state.overiggebouwdobject.entity);
  const loading = useAppSelector(state => state.overiggebouwdobject.loading);
  const updating = useAppSelector(state => state.overiggebouwdobject.updating);
  const updateSuccess = useAppSelector(state => state.overiggebouwdobject.updateSuccess);

  const handleClose = () => {
    navigate('/overiggebouwdobject');
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
      ...overiggebouwdobjectEntity,
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
          ...overiggebouwdobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.overiggebouwdobject.home.createOrEditLabel" data-cy="OveriggebouwdobjectCreateUpdateHeading">
            Create or edit a Overiggebouwdobject
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
                <ValidatedField name="id" required readOnly id="overiggebouwdobject-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Bouwjaar" id="overiggebouwdobject-bouwjaar" name="bouwjaar" data-cy="bouwjaar" type="text" />
              <ValidatedField
                label="Indicatieplanobject"
                id="overiggebouwdobject-indicatieplanobject"
                name="indicatieplanobject"
                data-cy="indicatieplanobject"
                type="text"
              />
              <ValidatedField
                label="Overiggebouwdobjectidentificatie"
                id="overiggebouwdobject-overiggebouwdobjectidentificatie"
                name="overiggebouwdobjectidentificatie"
                data-cy="overiggebouwdobjectidentificatie"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/overiggebouwdobject" replace color="info">
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

export default OveriggebouwdobjectUpdate;
