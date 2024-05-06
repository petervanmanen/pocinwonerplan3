import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVestigingvanzaakbehandelendeorganisatie } from 'app/shared/model/vestigingvanzaakbehandelendeorganisatie.model';
import { getEntity, updateEntity, createEntity, reset } from './vestigingvanzaakbehandelendeorganisatie.reducer';

export const VestigingvanzaakbehandelendeorganisatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vestigingvanzaakbehandelendeorganisatieEntity = useAppSelector(state => state.vestigingvanzaakbehandelendeorganisatie.entity);
  const loading = useAppSelector(state => state.vestigingvanzaakbehandelendeorganisatie.loading);
  const updating = useAppSelector(state => state.vestigingvanzaakbehandelendeorganisatie.updating);
  const updateSuccess = useAppSelector(state => state.vestigingvanzaakbehandelendeorganisatie.updateSuccess);

  const handleClose = () => {
    navigate('/vestigingvanzaakbehandelendeorganisatie');
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
      ...vestigingvanzaakbehandelendeorganisatieEntity,
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
          ...vestigingvanzaakbehandelendeorganisatieEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.vestigingvanzaakbehandelendeorganisatie.home.createOrEditLabel"
            data-cy="VestigingvanzaakbehandelendeorganisatieCreateUpdateHeading"
          >
            Create or edit a Vestigingvanzaakbehandelendeorganisatie
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="vestigingvanzaakbehandelendeorganisatie-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/vestigingvanzaakbehandelendeorganisatie"
                replace
                color="info"
              >
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

export default VestigingvanzaakbehandelendeorganisatieUpdate;
