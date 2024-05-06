import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAardzakelijkrecht } from 'app/shared/model/aardzakelijkrecht.model';
import { getEntity, updateEntity, createEntity, reset } from './aardzakelijkrecht.reducer';

export const AardzakelijkrechtUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aardzakelijkrechtEntity = useAppSelector(state => state.aardzakelijkrecht.entity);
  const loading = useAppSelector(state => state.aardzakelijkrecht.loading);
  const updating = useAppSelector(state => state.aardzakelijkrecht.updating);
  const updateSuccess = useAppSelector(state => state.aardzakelijkrecht.updateSuccess);

  const handleClose = () => {
    navigate('/aardzakelijkrecht');
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
      ...aardzakelijkrechtEntity,
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
          ...aardzakelijkrechtEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aardzakelijkrecht.home.createOrEditLabel" data-cy="AardzakelijkrechtCreateUpdateHeading">
            Create or edit a Aardzakelijkrecht
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
                <ValidatedField name="id" required readOnly id="aardzakelijkrecht-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Codeaardzakelijkrecht"
                id="aardzakelijkrecht-codeaardzakelijkrecht"
                name="codeaardzakelijkrecht"
                data-cy="codeaardzakelijkrecht"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheidaardzakelijkrecht"
                id="aardzakelijkrecht-datumbegingeldigheidaardzakelijkrecht"
                name="datumbegingeldigheidaardzakelijkrecht"
                data-cy="datumbegingeldigheidaardzakelijkrecht"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidaardzakelijkrecht"
                id="aardzakelijkrecht-datumeindegeldigheidaardzakelijkrecht"
                name="datumeindegeldigheidaardzakelijkrecht"
                data-cy="datumeindegeldigheidaardzakelijkrecht"
                type="date"
              />
              <ValidatedField
                label="Naamaardzakelijkrecht"
                id="aardzakelijkrecht-naamaardzakelijkrecht"
                name="naamaardzakelijkrecht"
                data-cy="naamaardzakelijkrecht"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aardzakelijkrecht" replace color="info">
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

export default AardzakelijkrechtUpdate;
