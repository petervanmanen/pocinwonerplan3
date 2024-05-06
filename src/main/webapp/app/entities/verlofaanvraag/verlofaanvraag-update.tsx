import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerlofaanvraag } from 'app/shared/model/verlofaanvraag.model';
import { getEntity, updateEntity, createEntity, reset } from './verlofaanvraag.reducer';

export const VerlofaanvraagUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verlofaanvraagEntity = useAppSelector(state => state.verlofaanvraag.entity);
  const loading = useAppSelector(state => state.verlofaanvraag.loading);
  const updating = useAppSelector(state => state.verlofaanvraag.updating);
  const updateSuccess = useAppSelector(state => state.verlofaanvraag.updateSuccess);

  const handleClose = () => {
    navigate('/verlofaanvraag');
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
      ...verlofaanvraagEntity,
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
          ...verlofaanvraagEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verlofaanvraag.home.createOrEditLabel" data-cy="VerlofaanvraagCreateUpdateHeading">
            Create or edit a Verlofaanvraag
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
                <ValidatedField name="id" required readOnly id="verlofaanvraag-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datumstart" id="verlofaanvraag-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Datumtot" id="verlofaanvraag-datumtot" name="datumtot" data-cy="datumtot" type="date" />
              <ValidatedField label="Soortverlof" id="verlofaanvraag-soortverlof" name="soortverlof" data-cy="soortverlof" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verlofaanvraag" replace color="info">
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

export default VerlofaanvraagUpdate;
