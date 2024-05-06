import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFormulierverlenginginhuur } from 'app/shared/model/formulierverlenginginhuur.model';
import { getEntity, updateEntity, createEntity, reset } from './formulierverlenginginhuur.reducer';

export const FormulierverlenginginhuurUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const formulierverlenginginhuurEntity = useAppSelector(state => state.formulierverlenginginhuur.entity);
  const loading = useAppSelector(state => state.formulierverlenginginhuur.loading);
  const updating = useAppSelector(state => state.formulierverlenginginhuur.updating);
  const updateSuccess = useAppSelector(state => state.formulierverlenginginhuur.updateSuccess);

  const handleClose = () => {
    navigate('/formulierverlenginginhuur');
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
      ...formulierverlenginginhuurEntity,
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
          ...formulierverlenginginhuurEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.formulierverlenginginhuur.home.createOrEditLabel" data-cy="FormulierverlenginginhuurCreateUpdateHeading">
            Create or edit a Formulierverlenginginhuur
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
                <ValidatedField name="id" required readOnly id="formulierverlenginginhuur-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumeindenieuw"
                id="formulierverlenginginhuur-datumeindenieuw"
                name="datumeindenieuw"
                data-cy="datumeindenieuw"
                type="date"
              />
              <ValidatedField
                label="Indicatieredeninhuurgewijzigd"
                id="formulierverlenginginhuur-indicatieredeninhuurgewijzigd"
                name="indicatieredeninhuurgewijzigd"
                data-cy="indicatieredeninhuurgewijzigd"
                type="text"
              />
              <ValidatedField
                label="Indicatieverhogeninkooporder"
                id="formulierverlenginginhuur-indicatieverhogeninkooporder"
                name="indicatieverhogeninkooporder"
                data-cy="indicatieverhogeninkooporder"
                type="text"
              />
              <ValidatedField
                label="Toelichting"
                id="formulierverlenginginhuur-toelichting"
                name="toelichting"
                data-cy="toelichting"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/formulierverlenginginhuur" replace color="info">
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

export default FormulierverlenginginhuurUpdate;
