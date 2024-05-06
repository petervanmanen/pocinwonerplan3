import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IZakelijkrecht } from 'app/shared/model/zakelijkrecht.model';
import { getEntity, updateEntity, createEntity, reset } from './zakelijkrecht.reducer';

export const ZakelijkrechtUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const zakelijkrechtEntity = useAppSelector(state => state.zakelijkrecht.entity);
  const loading = useAppSelector(state => state.zakelijkrecht.loading);
  const updating = useAppSelector(state => state.zakelijkrecht.updating);
  const updateSuccess = useAppSelector(state => state.zakelijkrecht.updateSuccess);

  const handleClose = () => {
    navigate('/zakelijkrecht');
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
    if (values.kosten !== undefined && typeof values.kosten !== 'number') {
      values.kosten = Number(values.kosten);
    }

    const entity = {
      ...zakelijkrechtEntity,
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
          ...zakelijkrechtEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.zakelijkrecht.home.createOrEditLabel" data-cy="ZakelijkrechtCreateUpdateHeading">
            Create or edit a Zakelijkrecht
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
                <ValidatedField name="id" required readOnly id="zakelijkrecht-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datumeinde" id="zakelijkrecht-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="zakelijkrecht-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Kosten" id="zakelijkrecht-kosten" name="kosten" data-cy="kosten" type="text" />
              <ValidatedField label="Soort" id="zakelijkrecht-soort" name="soort" data-cy="soort" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/zakelijkrecht" replace color="info">
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

export default ZakelijkrechtUpdate;
