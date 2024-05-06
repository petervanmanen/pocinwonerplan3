import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAanvraagofmelding } from 'app/shared/model/aanvraagofmelding.model';
import { getEntity, updateEntity, createEntity, reset } from './aanvraagofmelding.reducer';

export const AanvraagofmeldingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aanvraagofmeldingEntity = useAppSelector(state => state.aanvraagofmelding.entity);
  const loading = useAppSelector(state => state.aanvraagofmelding.loading);
  const updating = useAppSelector(state => state.aanvraagofmelding.updating);
  const updateSuccess = useAppSelector(state => state.aanvraagofmelding.updateSuccess);

  const handleClose = () => {
    navigate('/aanvraagofmelding');
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
      ...aanvraagofmeldingEntity,
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
          ...aanvraagofmeldingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aanvraagofmelding.home.createOrEditLabel" data-cy="AanvraagofmeldingCreateUpdateHeading">
            Create or edit a Aanvraagofmelding
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
                <ValidatedField name="id" required readOnly id="aanvraagofmelding-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datum" id="aanvraagofmelding-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField label="Opmerkingen" id="aanvraagofmelding-opmerkingen" name="opmerkingen" data-cy="opmerkingen" type="text" />
              <ValidatedField label="Reden" id="aanvraagofmelding-reden" name="reden" data-cy="reden" type="text" />
              <ValidatedField
                label="Soortverzuimofaanvraag"
                id="aanvraagofmelding-soortverzuimofaanvraag"
                name="soortverzuimofaanvraag"
                data-cy="soortverzuimofaanvraag"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aanvraagofmelding" replace color="info">
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

export default AanvraagofmeldingUpdate;
