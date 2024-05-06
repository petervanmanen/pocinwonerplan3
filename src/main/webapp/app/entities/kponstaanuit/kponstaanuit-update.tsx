import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKponstaanuit } from 'app/shared/model/kponstaanuit.model';
import { getEntity, updateEntity, createEntity, reset } from './kponstaanuit.reducer';

export const KponstaanuitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kponstaanuitEntity = useAppSelector(state => state.kponstaanuit.entity);
  const loading = useAppSelector(state => state.kponstaanuit.loading);
  const updating = useAppSelector(state => state.kponstaanuit.updating);
  const updateSuccess = useAppSelector(state => state.kponstaanuit.updateSuccess);

  const handleClose = () => {
    navigate('/kponstaanuit');
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
      ...kponstaanuitEntity,
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
          ...kponstaanuitEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.kponstaanuit.home.createOrEditLabel" data-cy="KponstaanuitCreateUpdateHeading">
            Create or edit a Kponstaanuit
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="kponstaanuit-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumbegingeldigheid"
                id="kponstaanuit-datumbegingeldigheid"
                name="datumbegingeldigheid"
                data-cy="datumbegingeldigheid"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="kponstaanuit-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kponstaanuit" replace color="info">
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

export default KponstaanuitUpdate;
