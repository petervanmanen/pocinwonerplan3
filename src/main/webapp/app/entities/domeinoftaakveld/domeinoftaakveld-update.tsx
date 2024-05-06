import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDomeinoftaakveld } from 'app/shared/model/domeinoftaakveld.model';
import { getEntity, updateEntity, createEntity, reset } from './domeinoftaakveld.reducer';

export const DomeinoftaakveldUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const domeinoftaakveldEntity = useAppSelector(state => state.domeinoftaakveld.entity);
  const loading = useAppSelector(state => state.domeinoftaakveld.loading);
  const updating = useAppSelector(state => state.domeinoftaakveld.updating);
  const updateSuccess = useAppSelector(state => state.domeinoftaakveld.updateSuccess);

  const handleClose = () => {
    navigate('/domeinoftaakveld');
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
      ...domeinoftaakveldEntity,
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
          ...domeinoftaakveldEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.domeinoftaakveld.home.createOrEditLabel" data-cy="DomeinoftaakveldCreateUpdateHeading">
            Create or edit a Domeinoftaakveld
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
                <ValidatedField name="id" required readOnly id="domeinoftaakveld-id" label="ID" validate={{ required: true }} />
              ) : null}
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/domeinoftaakveld" replace color="info">
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

export default DomeinoftaakveldUpdate;
