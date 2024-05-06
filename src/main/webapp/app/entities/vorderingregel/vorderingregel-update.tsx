import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVorderingregel } from 'app/shared/model/vorderingregel.model';
import { getEntity, updateEntity, createEntity, reset } from './vorderingregel.reducer';

export const VorderingregelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vorderingregelEntity = useAppSelector(state => state.vorderingregel.entity);
  const loading = useAppSelector(state => state.vorderingregel.loading);
  const updating = useAppSelector(state => state.vorderingregel.updating);
  const updateSuccess = useAppSelector(state => state.vorderingregel.updateSuccess);

  const handleClose = () => {
    navigate('/vorderingregel');
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
      ...vorderingregelEntity,
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
          ...vorderingregelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vorderingregel.home.createOrEditLabel" data-cy="VorderingregelCreateUpdateHeading">
            Create or edit a Vorderingregel
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
                <ValidatedField name="id" required readOnly id="vorderingregel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aangemaaktdoor"
                id="vorderingregel-aangemaaktdoor"
                name="aangemaaktdoor"
                data-cy="aangemaaktdoor"
                type="text"
              />
              <ValidatedField
                label="Aanmaakdatum"
                id="vorderingregel-aanmaakdatum"
                name="aanmaakdatum"
                data-cy="aanmaakdatum"
                type="date"
              />
              <ValidatedField
                label="Bedragexclbtw"
                id="vorderingregel-bedragexclbtw"
                name="bedragexclbtw"
                data-cy="bedragexclbtw"
                type="text"
              />
              <ValidatedField
                label="Bedraginclbtw"
                id="vorderingregel-bedraginclbtw"
                name="bedraginclbtw"
                data-cy="bedraginclbtw"
                type="text"
              />
              <ValidatedField
                label="Btwcategorie"
                id="vorderingregel-btwcategorie"
                name="btwcategorie"
                data-cy="btwcategorie"
                type="text"
              />
              <ValidatedField
                label="Gemuteerddoor"
                id="vorderingregel-gemuteerddoor"
                name="gemuteerddoor"
                data-cy="gemuteerddoor"
                type="text"
              />
              <ValidatedField
                label="Mutatiedatum"
                id="vorderingregel-mutatiedatum"
                name="mutatiedatum"
                data-cy="mutatiedatum"
                type="date"
              />
              <ValidatedField
                label="Omschrijving"
                id="vorderingregel-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField label="Periodiek" id="vorderingregel-periodiek" name="periodiek" data-cy="periodiek" type="text" />
              <ValidatedField label="Type" id="vorderingregel-type" name="type" data-cy="type" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vorderingregel" replace color="info">
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

export default VorderingregelUpdate;
