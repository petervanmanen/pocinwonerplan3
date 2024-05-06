import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFractie } from 'app/shared/model/fractie.model';
import { getEntities as getFracties } from 'app/entities/fractie/fractie.reducer';
import { IPas } from 'app/shared/model/pas.model';
import { getEntities as getPas } from 'app/entities/pas/pas.reducer';
import { IMilieustraat } from 'app/shared/model/milieustraat.model';
import { getEntity, updateEntity, createEntity, reset } from './milieustraat.reducer';

export const MilieustraatUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fracties = useAppSelector(state => state.fractie.entities);
  const pas = useAppSelector(state => state.pas.entities);
  const milieustraatEntity = useAppSelector(state => state.milieustraat.entity);
  const loading = useAppSelector(state => state.milieustraat.loading);
  const updating = useAppSelector(state => state.milieustraat.updating);
  const updateSuccess = useAppSelector(state => state.milieustraat.updateSuccess);

  const handleClose = () => {
    navigate('/milieustraat');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFracties({}));
    dispatch(getPas({}));
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
      ...milieustraatEntity,
      ...values,
      inzamelpuntvanFracties: mapIdList(values.inzamelpuntvanFracties),
      geldigvoorPas: mapIdList(values.geldigvoorPas),
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
          ...milieustraatEntity,
          inzamelpuntvanFracties: milieustraatEntity?.inzamelpuntvanFracties?.map(e => e.id.toString()),
          geldigvoorPas: milieustraatEntity?.geldigvoorPas?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.milieustraat.home.createOrEditLabel" data-cy="MilieustraatCreateUpdateHeading">
            Create or edit a Milieustraat
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="milieustraat-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Adresaanduiding"
                id="milieustraat-adresaanduiding"
                name="adresaanduiding"
                data-cy="adresaanduiding"
                type="text"
              />
              <ValidatedField label="Naam" id="milieustraat-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="milieustraat-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Inzamelpuntvan Fractie"
                id="milieustraat-inzamelpuntvanFractie"
                data-cy="inzamelpuntvanFractie"
                type="select"
                multiple
                name="inzamelpuntvanFracties"
              >
                <option value="" key="0" />
                {fracties
                  ? fracties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Geldigvoor Pas"
                id="milieustraat-geldigvoorPas"
                data-cy="geldigvoorPas"
                type="select"
                multiple
                name="geldigvoorPas"
              >
                <option value="" key="0" />
                {pas
                  ? pas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/milieustraat" replace color="info">
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

export default MilieustraatUpdate;
