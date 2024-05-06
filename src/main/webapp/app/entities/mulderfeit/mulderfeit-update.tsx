import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMulderfeit } from 'app/shared/model/mulderfeit.model';
import { getEntity, updateEntity, createEntity, reset } from './mulderfeit.reducer';

export const MulderfeitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const mulderfeitEntity = useAppSelector(state => state.mulderfeit.entity);
  const loading = useAppSelector(state => state.mulderfeit.loading);
  const updating = useAppSelector(state => state.mulderfeit.updating);
  const updateSuccess = useAppSelector(state => state.mulderfeit.updateSuccess);

  const handleClose = () => {
    navigate('/mulderfeit');
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
    if (values.bedrag !== undefined && typeof values.bedrag !== 'number') {
      values.bedrag = Number(values.bedrag);
    }
    if (values.parkeertarief !== undefined && typeof values.parkeertarief !== 'number') {
      values.parkeertarief = Number(values.parkeertarief);
    }

    const entity = {
      ...mulderfeitEntity,
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
          ...mulderfeitEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.mulderfeit.home.createOrEditLabel" data-cy="MulderfeitCreateUpdateHeading">
            Create or edit a Mulderfeit
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="mulderfeit-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Bedrag" id="mulderfeit-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField
                label="Bezwaarafgehandeld"
                id="mulderfeit-bezwaarafgehandeld"
                name="bezwaarafgehandeld"
                data-cy="bezwaarafgehandeld"
                type="date"
              />
              <ValidatedField
                label="Bezwaaringetrokken"
                id="mulderfeit-bezwaaringetrokken"
                name="bezwaaringetrokken"
                data-cy="bezwaaringetrokken"
                type="date"
              />
              <ValidatedField
                label="Bezwaartoegewezen"
                id="mulderfeit-bezwaartoegewezen"
                name="bezwaartoegewezen"
                data-cy="bezwaartoegewezen"
                type="date"
              />
              <ValidatedField label="Bonnummer" id="mulderfeit-bonnummer" name="bonnummer" data-cy="bonnummer" type="text" />
              <ValidatedField
                label="Datumbetaling"
                id="mulderfeit-datumbetaling"
                name="datumbetaling"
                data-cy="datumbetaling"
                type="date"
              />
              <ValidatedField label="Datumbezwaar" id="mulderfeit-datumbezwaar" name="datumbezwaar" data-cy="datumbezwaar" type="date" />
              <ValidatedField
                label="Datumgeseponeerd"
                id="mulderfeit-datumgeseponeerd"
                name="datumgeseponeerd"
                data-cy="datumgeseponeerd"
                type="date"
              />
              <ValidatedField
                label="Datumindiening"
                id="mulderfeit-datumindiening"
                name="datumindiening"
                data-cy="datumindiening"
                type="date"
              />
              <ValidatedField label="Dienstcd" id="mulderfeit-dienstcd" name="dienstcd" data-cy="dienstcd" type="text" />
              <ValidatedField label="Organisatie" id="mulderfeit-organisatie" name="organisatie" data-cy="organisatie" type="text" />
              <ValidatedField label="Overtreding" id="mulderfeit-overtreding" name="overtreding" data-cy="overtreding" type="text" />
              <ValidatedField
                label="Parkeertarief"
                id="mulderfeit-parkeertarief"
                name="parkeertarief"
                data-cy="parkeertarief"
                type="text"
              />
              <ValidatedField
                label="Redenseponeren"
                id="mulderfeit-redenseponeren"
                name="redenseponeren"
                data-cy="redenseponeren"
                type="text"
              />
              <ValidatedField
                label="Vorderingnummer"
                id="mulderfeit-vorderingnummer"
                name="vorderingnummer"
                data-cy="vorderingnummer"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/mulderfeit" replace color="info">
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

export default MulderfeitUpdate;
