import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITenaamstelling } from 'app/shared/model/tenaamstelling.model';
import { getEntities as getTenaamstellings } from 'app/entities/tenaamstelling/tenaamstelling.reducer';
import { IAantekening } from 'app/shared/model/aantekening.model';
import { getEntity, updateEntity, createEntity, reset } from './aantekening.reducer';

export const AantekeningUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tenaamstellings = useAppSelector(state => state.tenaamstelling.entities);
  const aantekeningEntity = useAppSelector(state => state.aantekening.entity);
  const loading = useAppSelector(state => state.aantekening.loading);
  const updating = useAppSelector(state => state.aantekening.updating);
  const updateSuccess = useAppSelector(state => state.aantekening.updateSuccess);

  const handleClose = () => {
    navigate('/aantekening');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTenaamstellings({}));
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
      ...aantekeningEntity,
      ...values,
      emptyTenaamstelling: tenaamstellings.find(it => it.id.toString() === values.emptyTenaamstelling?.toString()),
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
          ...aantekeningEntity,
          emptyTenaamstelling: aantekeningEntity?.emptyTenaamstelling?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aantekening.home.createOrEditLabel" data-cy="AantekeningCreateUpdateHeading">
            Create or edit a Aantekening
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="aantekening-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aard"
                id="aantekening-aard"
                name="aard"
                data-cy="aard"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField label="Begrenzing" id="aantekening-begrenzing" name="begrenzing" data-cy="begrenzing" type="text" />
              <ValidatedField
                label="Betreftgedeeltevanperceel"
                id="aantekening-betreftgedeeltevanperceel"
                name="betreftgedeeltevanperceel"
                data-cy="betreftgedeeltevanperceel"
                check
                type="checkbox"
              />
              <ValidatedField label="Datumeinde" id="aantekening-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Datumeinderecht"
                id="aantekening-datumeinderecht"
                name="datumeinderecht"
                data-cy="datumeinderecht"
                type="date"
              />
              <ValidatedField
                label="Identificatie"
                id="aantekening-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField label="Omschrijving" id="aantekening-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="aantekening-emptyTenaamstelling"
                name="emptyTenaamstelling"
                data-cy="emptyTenaamstelling"
                label="Empty Tenaamstelling"
                type="select"
              >
                <option value="" key="0" />
                {tenaamstellings
                  ? tenaamstellings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aantekening" replace color="info">
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

export default AantekeningUpdate;
