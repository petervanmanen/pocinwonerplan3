import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IElog } from 'app/shared/model/elog.model';
import { getEntity, updateEntity, createEntity, reset } from './elog.reducer';

export const ElogUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const elogEntity = useAppSelector(state => state.elog.entity);
  const loading = useAppSelector(state => state.elog.loading);
  const updating = useAppSelector(state => state.elog.updating);
  const updateSuccess = useAppSelector(state => state.elog.updateSuccess);

  const handleClose = () => {
    navigate('/elog');
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
      ...elogEntity,
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
          ...elogEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.elog.home.createOrEditLabel" data-cy="ElogCreateUpdateHeading">
            Create or edit a Elog
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="elog-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Korteomschrijving"
                id="elog-korteomschrijving"
                name="korteomschrijving"
                data-cy="korteomschrijving"
                type="text"
              />
              <ValidatedField label="Omschrijving" id="elog-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Tijd" id="elog-tijd" name="tijd" data-cy="tijd" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/elog" replace color="info">
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

export default ElogUpdate;
