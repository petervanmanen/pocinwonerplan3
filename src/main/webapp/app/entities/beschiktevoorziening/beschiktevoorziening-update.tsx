import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBeschiktevoorziening } from 'app/shared/model/beschiktevoorziening.model';
import { getEntity, updateEntity, createEntity, reset } from './beschiktevoorziening.reducer';

export const BeschiktevoorzieningUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const beschiktevoorzieningEntity = useAppSelector(state => state.beschiktevoorziening.entity);
  const loading = useAppSelector(state => state.beschiktevoorziening.loading);
  const updating = useAppSelector(state => state.beschiktevoorziening.updating);
  const updateSuccess = useAppSelector(state => state.beschiktevoorziening.updateSuccess);

  const handleClose = () => {
    navigate('/beschiktevoorziening');
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
      ...beschiktevoorzieningEntity,
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
          ...beschiktevoorzieningEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.beschiktevoorziening.home.createOrEditLabel" data-cy="BeschiktevoorzieningCreateUpdateHeading">
            Create or edit a Beschiktevoorziening
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
                <ValidatedField name="id" required readOnly id="beschiktevoorziening-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Code"
                id="beschiktevoorziening-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Datumeinde" id="beschiktevoorziening-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Datumeindeoorspronkelijk"
                id="beschiktevoorziening-datumeindeoorspronkelijk"
                name="datumeindeoorspronkelijk"
                data-cy="datumeindeoorspronkelijk"
                type="date"
              />
              <ValidatedField label="Datumstart" id="beschiktevoorziening-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Eenheid" id="beschiktevoorziening-eenheid" name="eenheid" data-cy="eenheid" type="text" />
              <ValidatedField label="Frequentie" id="beschiktevoorziening-frequentie" name="frequentie" data-cy="frequentie" type="text" />
              <ValidatedField
                label="Leveringsvorm"
                id="beschiktevoorziening-leveringsvorm"
                name="leveringsvorm"
                data-cy="leveringsvorm"
                type="text"
              />
              <ValidatedField label="Omvang" id="beschiktevoorziening-omvang" name="omvang" data-cy="omvang" type="text" />
              <ValidatedField
                label="Redeneinde"
                id="beschiktevoorziening-redeneinde"
                name="redeneinde"
                data-cy="redeneinde"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField label="Status" id="beschiktevoorziening-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Wet" id="beschiktevoorziening-wet" name="wet" data-cy="wet" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/beschiktevoorziening" replace color="info">
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

export default BeschiktevoorzieningUpdate;
