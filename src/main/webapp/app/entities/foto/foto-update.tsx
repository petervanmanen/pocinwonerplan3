import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFoto } from 'app/shared/model/foto.model';
import { getEntity, updateEntity, createEntity, reset } from './foto.reducer';

export const FotoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fotoEntity = useAppSelector(state => state.foto.entity);
  const loading = useAppSelector(state => state.foto.loading);
  const updating = useAppSelector(state => state.foto.updating);
  const updateSuccess = useAppSelector(state => state.foto.updateSuccess);

  const handleClose = () => {
    navigate('/foto');
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
      ...fotoEntity,
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
          ...fotoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.foto.home.createOrEditLabel" data-cy="FotoCreateUpdateHeading">
            Create or edit a Foto
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="foto-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Bestandsgrootte"
                id="foto-bestandsgrootte"
                name="bestandsgrootte"
                data-cy="bestandsgrootte"
                type="text"
              />
              <ValidatedField label="Bestandsnaam" id="foto-bestandsnaam" name="bestandsnaam" data-cy="bestandsnaam" type="text" />
              <ValidatedField label="Bestandstype" id="foto-bestandstype" name="bestandstype" data-cy="bestandstype" type="text" />
              <ValidatedField label="Datumtijd" id="foto-datumtijd" name="datumtijd" data-cy="datumtijd" type="text" />
              <ValidatedField label="Locatie" id="foto-locatie" name="locatie" data-cy="locatie" type="text" />
              <ValidatedField label="Pixelsx" id="foto-pixelsx" name="pixelsx" data-cy="pixelsx" type="text" />
              <ValidatedField label="Pixelsy" id="foto-pixelsy" name="pixelsy" data-cy="pixelsy" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/foto" replace color="info">
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

export default FotoUpdate;
