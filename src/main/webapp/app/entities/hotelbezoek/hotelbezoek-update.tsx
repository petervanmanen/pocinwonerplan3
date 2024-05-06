import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHotel } from 'app/shared/model/hotel.model';
import { getEntities as getHotels } from 'app/entities/hotel/hotel.reducer';
import { IHotelbezoek } from 'app/shared/model/hotelbezoek.model';
import { getEntity, updateEntity, createEntity, reset } from './hotelbezoek.reducer';

export const HotelbezoekUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const hotels = useAppSelector(state => state.hotel.entities);
  const hotelbezoekEntity = useAppSelector(state => state.hotelbezoek.entity);
  const loading = useAppSelector(state => state.hotelbezoek.loading);
  const updating = useAppSelector(state => state.hotelbezoek.updating);
  const updateSuccess = useAppSelector(state => state.hotelbezoek.updateSuccess);

  const handleClose = () => {
    navigate('/hotelbezoek');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getHotels({}));
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
      ...hotelbezoekEntity,
      ...values,
      heeftHotel: hotels.find(it => it.id.toString() === values.heeftHotel?.toString()),
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
          ...hotelbezoekEntity,
          heeftHotel: hotelbezoekEntity?.heeftHotel?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.hotelbezoek.home.createOrEditLabel" data-cy="HotelbezoekCreateUpdateHeading">
            Create or edit a Hotelbezoek
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="hotelbezoek-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datumeinde" id="hotelbezoek-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="hotelbezoek-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField id="hotelbezoek-heeftHotel" name="heeftHotel" data-cy="heeftHotel" label="Heeft Hotel" type="select">
                <option value="" key="0" />
                {hotels
                  ? hotels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/hotelbezoek" replace color="info">
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

export default HotelbezoekUpdate;
