import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeverancier } from 'app/shared/model/leverancier.model';
import { getEntities as getLeveranciers } from 'app/entities/leverancier/leverancier.reducer';
import { IBeschikking } from 'app/shared/model/beschikking.model';
import { getEntities as getBeschikkings } from 'app/entities/beschikking/beschikking.reducer';
import { IToewijzing } from 'app/shared/model/toewijzing.model';
import { getEntity, updateEntity, createEntity, reset } from './toewijzing.reducer';

export const ToewijzingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const beschikkings = useAppSelector(state => state.beschikking.entities);
  const toewijzingEntity = useAppSelector(state => state.toewijzing.entity);
  const loading = useAppSelector(state => state.toewijzing.loading);
  const updating = useAppSelector(state => state.toewijzing.updating);
  const updateSuccess = useAppSelector(state => state.toewijzing.updateSuccess);

  const handleClose = () => {
    navigate('/toewijzing');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeveranciers({}));
    dispatch(getBeschikkings({}));
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
      ...toewijzingEntity,
      ...values,
      levertvoorzieningLeverancier: leveranciers.find(it => it.id.toString() === values.levertvoorzieningLeverancier?.toString()),
      toewijzingBeschikking: beschikkings.find(it => it.id.toString() === values.toewijzingBeschikking?.toString()),
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
          ...toewijzingEntity,
          levertvoorzieningLeverancier: toewijzingEntity?.levertvoorzieningLeverancier?.id,
          toewijzingBeschikking: toewijzingEntity?.toewijzingBeschikking?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.toewijzing.home.createOrEditLabel" data-cy="ToewijzingCreateUpdateHeading">
            Create or edit a Toewijzing
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="toewijzing-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Code"
                id="toewijzing-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Commentaar" id="toewijzing-commentaar" name="commentaar" data-cy="commentaar" type="text" />
              <ValidatedField
                label="Datumaanschaf"
                id="toewijzing-datumaanschaf"
                name="datumaanschaf"
                data-cy="datumaanschaf"
                type="date"
              />
              <ValidatedField
                label="Datumeindetoewijzing"
                id="toewijzing-datumeindetoewijzing"
                name="datumeindetoewijzing"
                data-cy="datumeindetoewijzing"
                type="date"
              />
              <ValidatedField
                label="Datumstarttoewijzing"
                id="toewijzing-datumstarttoewijzing"
                name="datumstarttoewijzing"
                data-cy="datumstarttoewijzing"
                type="date"
              />
              <ValidatedField
                label="Datumtoewijzing"
                id="toewijzing-datumtoewijzing"
                name="datumtoewijzing"
                data-cy="datumtoewijzing"
                type="date"
              />
              <ValidatedField label="Eenheid" id="toewijzing-eenheid" name="eenheid" data-cy="eenheid" type="text" />
              <ValidatedField label="Frequentie" id="toewijzing-frequentie" name="frequentie" data-cy="frequentie" type="text" />
              <ValidatedField label="Omvang" id="toewijzing-omvang" name="omvang" data-cy="omvang" type="text" />
              <ValidatedField
                label="Redenwijziging"
                id="toewijzing-redenwijziging"
                name="redenwijziging"
                data-cy="redenwijziging"
                type="text"
              />
              <ValidatedField
                label="Toewijzingnummer"
                id="toewijzing-toewijzingnummer"
                name="toewijzingnummer"
                data-cy="toewijzingnummer"
                type="text"
              />
              <ValidatedField label="Wet" id="toewijzing-wet" name="wet" data-cy="wet" type="text" />
              <ValidatedField
                id="toewijzing-levertvoorzieningLeverancier"
                name="levertvoorzieningLeverancier"
                data-cy="levertvoorzieningLeverancier"
                label="Levertvoorziening Leverancier"
                type="select"
              >
                <option value="" key="0" />
                {leveranciers
                  ? leveranciers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="toewijzing-toewijzingBeschikking"
                name="toewijzingBeschikking"
                data-cy="toewijzingBeschikking"
                label="Toewijzing Beschikking"
                type="select"
              >
                <option value="" key="0" />
                {beschikkings
                  ? beschikkings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/toewijzing" replace color="info">
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

export default ToewijzingUpdate;
