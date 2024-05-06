import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWozwaarde } from 'app/shared/model/wozwaarde.model';
import { getEntity, updateEntity, createEntity, reset } from './wozwaarde.reducer';

export const WozwaardeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const wozwaardeEntity = useAppSelector(state => state.wozwaarde.entity);
  const loading = useAppSelector(state => state.wozwaarde.loading);
  const updating = useAppSelector(state => state.wozwaarde.updating);
  const updateSuccess = useAppSelector(state => state.wozwaarde.updateSuccess);

  const handleClose = () => {
    navigate('/wozwaarde');
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
      ...wozwaardeEntity,
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
          ...wozwaardeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.wozwaarde.home.createOrEditLabel" data-cy="WozwaardeCreateUpdateHeading">
            Create or edit a Wozwaarde
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="wozwaarde-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumpeilingtoestand"
                id="wozwaarde-datumpeilingtoestand"
                name="datumpeilingtoestand"
                data-cy="datumpeilingtoestand"
                type="date"
              />
              <ValidatedField
                label="Datumwaardepeiling"
                id="wozwaarde-datumwaardepeiling"
                name="datumwaardepeiling"
                data-cy="datumwaardepeiling"
                type="date"
              />
              <ValidatedField
                label="Statusbeschikking"
                id="wozwaarde-statusbeschikking"
                name="statusbeschikking"
                data-cy="statusbeschikking"
                type="text"
              />
              <ValidatedField
                label="Vastgesteldewaarde"
                id="wozwaarde-vastgesteldewaarde"
                name="vastgesteldewaarde"
                data-cy="vastgesteldewaarde"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/wozwaarde" replace color="info">
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

export default WozwaardeUpdate;
