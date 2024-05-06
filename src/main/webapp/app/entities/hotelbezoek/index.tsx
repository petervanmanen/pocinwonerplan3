import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Hotelbezoek from './hotelbezoek';
import HotelbezoekDetail from './hotelbezoek-detail';
import HotelbezoekUpdate from './hotelbezoek-update';
import HotelbezoekDeleteDialog from './hotelbezoek-delete-dialog';

const HotelbezoekRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Hotelbezoek />} />
    <Route path="new" element={<HotelbezoekUpdate />} />
    <Route path=":id">
      <Route index element={<HotelbezoekDetail />} />
      <Route path="edit" element={<HotelbezoekUpdate />} />
      <Route path="delete" element={<HotelbezoekDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HotelbezoekRoutes;
