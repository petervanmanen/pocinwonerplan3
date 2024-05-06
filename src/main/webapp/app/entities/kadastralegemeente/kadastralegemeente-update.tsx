import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKadastralegemeente } from 'app/shared/model/kadastralegemeente.model';
import { getEntity, updateEntity, createEntity, reset } from './kadastralegemeente.reducer';

export const KadastralegemeenteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kadastralegemeenteEntity = useAppSelector(state => state.kadastralegemeente.entity);
  const loading = useAppSelector(state => state.kadastralegemeente.loading);
  const updating = useAppSelector(state => state.kadastralegemeente.updating);
  const updateSuccess = useAppSelector(state => state.kadastralegemeente.updateSuccess);

  const handleClose = () => {
    navigate('/kadastralegemeente');
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
      ...kadastralegemeenteEntity,
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
          ...kadastralegemeenteEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.kadastralegemeente.home.createOrEditLabel" data-cy="KadastralegemeenteCreateUpdateHeading">
            Create or edit a Kadastralegemeente
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
                <ValidatedField name="id" required readOnly id="kadastralegemeente-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheidkadastralegemeente"
                id="kadastralegemeente-datumbegingeldigheidkadastralegemeente"
                name="datumbegingeldigheidkadastralegemeente"
                data-cy="datumbegingeldigheidkadastralegemeente"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidkadastralegemeente"
                id="kadastralegemeente-datumeindegeldigheidkadastralegemeente"
                name="datumeindegeldigheidkadastralegemeente"
                data-cy="datumeindegeldigheidkadastralegemeente"
                type="date"
              />
              <ValidatedField
                label="Kadastralegemeentecode"
                id="kadastralegemeente-kadastralegemeentecode"
                name="kadastralegemeentecode"
                data-cy="kadastralegemeentecode"
                type="text"
              />
              <ValidatedField label="Naam" id="kadastralegemeente-naam" name="naam" data-cy="naam" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kadastralegemeente" replace color="info">
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

export default KadastralegemeenteUpdate;
