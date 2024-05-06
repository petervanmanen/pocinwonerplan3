import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IIndividueelkeuzebudget } from 'app/shared/model/individueelkeuzebudget.model';
import { getEntity, updateEntity, createEntity, reset } from './individueelkeuzebudget.reducer';

export const IndividueelkeuzebudgetUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const individueelkeuzebudgetEntity = useAppSelector(state => state.individueelkeuzebudget.entity);
  const loading = useAppSelector(state => state.individueelkeuzebudget.loading);
  const updating = useAppSelector(state => state.individueelkeuzebudget.updating);
  const updateSuccess = useAppSelector(state => state.individueelkeuzebudget.updateSuccess);

  const handleClose = () => {
    navigate('/individueelkeuzebudget');
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
    if (values.bedrag !== undefined && typeof values.bedrag !== 'number') {
      values.bedrag = Number(values.bedrag);
    }

    const entity = {
      ...individueelkeuzebudgetEntity,
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
          ...individueelkeuzebudgetEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.individueelkeuzebudget.home.createOrEditLabel" data-cy="IndividueelkeuzebudgetCreateUpdateHeading">
            Create or edit a Individueelkeuzebudget
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
                <ValidatedField name="id" required readOnly id="individueelkeuzebudget-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Bedrag" id="individueelkeuzebudget-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField
                label="Datumeinde"
                id="individueelkeuzebudget-datumeinde"
                name="datumeinde"
                data-cy="datumeinde"
                type="date"
              />
              <ValidatedField
                label="Datumstart"
                id="individueelkeuzebudget-datumstart"
                name="datumstart"
                data-cy="datumstart"
                type="date"
              />
              <ValidatedField
                label="Datumtoekenning"
                id="individueelkeuzebudget-datumtoekenning"
                name="datumtoekenning"
                data-cy="datumtoekenning"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/individueelkeuzebudget" replace color="info">
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

export default IndividueelkeuzebudgetUpdate;
