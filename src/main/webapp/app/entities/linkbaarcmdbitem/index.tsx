import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Linkbaarcmdbitem from './linkbaarcmdbitem';
import LinkbaarcmdbitemDetail from './linkbaarcmdbitem-detail';
import LinkbaarcmdbitemUpdate from './linkbaarcmdbitem-update';
import LinkbaarcmdbitemDeleteDialog from './linkbaarcmdbitem-delete-dialog';

const LinkbaarcmdbitemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Linkbaarcmdbitem />} />
    <Route path="new" element={<LinkbaarcmdbitemUpdate />} />
    <Route path=":id">
      <Route index element={<LinkbaarcmdbitemDetail />} />
      <Route path="edit" element={<LinkbaarcmdbitemUpdate />} />
      <Route path="delete" element={<LinkbaarcmdbitemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LinkbaarcmdbitemRoutes;
