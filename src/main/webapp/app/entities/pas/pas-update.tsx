import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMilieustraat } from 'app/shared/model/milieustraat.model';
import { getEntities as getMilieustraats } from 'app/entities/milieustraat/milieustraat.reducer';
import { IPas } from 'app/shared/model/pas.model';
import { getEntity, updateEntity, createEntity, reset } from './pas.reducer';

export const PasUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const milieustraats = useAppSelector(state => state.milieustraat.entities);
  const pasEntity = useAppSelector(state => state.pas.entity);
  const loading = useAppSelector(state => state.pas.loading);
  const updating = useAppSelector(state => state.pas.updating);
  const updateSuccess = useAppSelector(state => state.pas.updateSuccess);

  const handleClose = () => {
    navigate('/pas');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMilieustraats({}));
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
      ...pasEntity,
      ...values,
      geldigvoorMilieustraats: mapIdList(values.geldigvoorMilieustraats),
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
          ...pasEntity,
          geldigvoorMilieustraats: pasEntity?.geldigvoorMilieustraats?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.pas.home.createOrEditLabel" data-cy="PasCreateUpdateHeading">
            Create or edit a Pas
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="pas-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Adresaanduiding"
                id="pas-adresaanduiding"
                name="adresaanduiding"
                data-cy="adresaanduiding"
                type="text"
              />
              <ValidatedField label="Pasnummer" id="pas-pasnummer" name="pasnummer" data-cy="pasnummer" type="text" />
              <ValidatedField
                label="Geldigvoor Milieustraat"
                id="pas-geldigvoorMilieustraat"
                data-cy="geldigvoorMilieustraat"
                type="select"
                multiple
                name="geldigvoorMilieustraats"
              >
                <option value="" key="0" />
                {milieustraats
                  ? milieustraats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pas" replace color="info">
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

export default PasUpdate;
