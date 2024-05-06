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
import { IZekerheidsrecht } from 'app/shared/model/zekerheidsrecht.model';
import { getEntity, updateEntity, createEntity, reset } from './zekerheidsrecht.reducer';

export const ZekerheidsrechtUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tenaamstellings = useAppSelector(state => state.tenaamstelling.entities);
  const zekerheidsrechtEntity = useAppSelector(state => state.zekerheidsrecht.entity);
  const loading = useAppSelector(state => state.zekerheidsrecht.loading);
  const updating = useAppSelector(state => state.zekerheidsrecht.updating);
  const updateSuccess = useAppSelector(state => state.zekerheidsrecht.updateSuccess);

  const handleClose = () => {
    navigate('/zekerheidsrecht');
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
      ...zekerheidsrechtEntity,
      ...values,
      bezwaartTenaamstelling: tenaamstellings.find(it => it.id.toString() === values.bezwaartTenaamstelling?.toString()),
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
          ...zekerheidsrechtEntity,
          bezwaartTenaamstelling: zekerheidsrechtEntity?.bezwaartTenaamstelling?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.zekerheidsrecht.home.createOrEditLabel" data-cy="ZekerheidsrechtCreateUpdateHeading">
            Create or edit a Zekerheidsrecht
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
                <ValidatedField name="id" required readOnly id="zekerheidsrecht-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aandeelinbetrokkenrecht"
                id="zekerheidsrecht-aandeelinbetrokkenrecht"
                name="aandeelinbetrokkenrecht"
                data-cy="aandeelinbetrokkenrecht"
                type="text"
              />
              <ValidatedField
                label="Datumeinderecht"
                id="zekerheidsrecht-datumeinderecht"
                name="datumeinderecht"
                data-cy="datumeinderecht"
                type="date"
              />
              <ValidatedField
                label="Datumingangrecht"
                id="zekerheidsrecht-datumingangrecht"
                name="datumingangrecht"
                data-cy="datumingangrecht"
                type="date"
              />
              <ValidatedField
                label="Identificatiezekerheidsrecht"
                id="zekerheidsrecht-identificatiezekerheidsrecht"
                name="identificatiezekerheidsrecht"
                data-cy="identificatiezekerheidsrecht"
                type="text"
              />
              <ValidatedField
                label="Omschrijvingbetrokkenrecht"
                id="zekerheidsrecht-omschrijvingbetrokkenrecht"
                name="omschrijvingbetrokkenrecht"
                data-cy="omschrijvingbetrokkenrecht"
                type="text"
              />
              <ValidatedField
                label="Typezekerheidsrecht"
                id="zekerheidsrecht-typezekerheidsrecht"
                name="typezekerheidsrecht"
                data-cy="typezekerheidsrecht"
                type="text"
              />
              <ValidatedField
                id="zekerheidsrecht-bezwaartTenaamstelling"
                name="bezwaartTenaamstelling"
                data-cy="bezwaartTenaamstelling"
                label="Bezwaart Tenaamstelling"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/zekerheidsrecht" replace color="info">
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

export default ZekerheidsrechtUpdate;
