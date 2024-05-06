import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUitkeringsrun } from 'app/shared/model/uitkeringsrun.model';
import { getEntity, updateEntity, createEntity, reset } from './uitkeringsrun.reducer';

export const UitkeringsrunUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const uitkeringsrunEntity = useAppSelector(state => state.uitkeringsrun.entity);
  const loading = useAppSelector(state => state.uitkeringsrun.loading);
  const updating = useAppSelector(state => state.uitkeringsrun.updating);
  const updateSuccess = useAppSelector(state => state.uitkeringsrun.updateSuccess);

  const handleClose = () => {
    navigate('/uitkeringsrun');
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
      ...uitkeringsrunEntity,
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
          ...uitkeringsrunEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.uitkeringsrun.home.createOrEditLabel" data-cy="UitkeringsrunCreateUpdateHeading">
            Create or edit a Uitkeringsrun
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
                <ValidatedField name="id" required readOnly id="uitkeringsrun-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datumrun" id="uitkeringsrun-datumrun" name="datumrun" data-cy="datumrun" type="date" />
              <ValidatedField
                label="Frequentie"
                id="uitkeringsrun-frequentie"
                name="frequentie"
                data-cy="frequentie"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Perioderun"
                id="uitkeringsrun-perioderun"
                name="perioderun"
                data-cy="perioderun"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Soortrun" id="uitkeringsrun-soortrun" name="soortrun" data-cy="soortrun" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/uitkeringsrun" replace color="info">
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

export default UitkeringsrunUpdate;
