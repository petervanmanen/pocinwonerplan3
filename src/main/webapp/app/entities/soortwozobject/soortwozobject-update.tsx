import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISoortwozobject } from 'app/shared/model/soortwozobject.model';
import { getEntity, updateEntity, createEntity, reset } from './soortwozobject.reducer';

export const SoortwozobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const soortwozobjectEntity = useAppSelector(state => state.soortwozobject.entity);
  const loading = useAppSelector(state => state.soortwozobject.loading);
  const updating = useAppSelector(state => state.soortwozobject.updating);
  const updateSuccess = useAppSelector(state => state.soortwozobject.updateSuccess);

  const handleClose = () => {
    navigate('/soortwozobject');
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
      ...soortwozobjectEntity,
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
          ...soortwozobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.soortwozobject.home.createOrEditLabel" data-cy="SoortwozobjectCreateUpdateHeading">
            Create or edit a Soortwozobject
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
                <ValidatedField name="id" required readOnly id="soortwozobject-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheidsoortobjectcode"
                id="soortwozobject-datumbegingeldigheidsoortobjectcode"
                name="datumbegingeldigheidsoortobjectcode"
                data-cy="datumbegingeldigheidsoortobjectcode"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidsoortobjectcode"
                id="soortwozobject-datumeindegeldigheidsoortobjectcode"
                name="datumeindegeldigheidsoortobjectcode"
                data-cy="datumeindegeldigheidsoortobjectcode"
                type="date"
              />
              <ValidatedField
                label="Naamsoortobjectcode"
                id="soortwozobject-naamsoortobjectcode"
                name="naamsoortobjectcode"
                data-cy="naamsoortobjectcode"
                type="text"
              />
              <ValidatedField
                label="Opmerkingensoortobjectcode"
                id="soortwozobject-opmerkingensoortobjectcode"
                name="opmerkingensoortobjectcode"
                data-cy="opmerkingensoortobjectcode"
                type="text"
              />
              <ValidatedField
                label="Soortobjectcode"
                id="soortwozobject-soortobjectcode"
                name="soortobjectcode"
                data-cy="soortobjectcode"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/soortwozobject" replace color="info">
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

export default SoortwozobjectUpdate;
